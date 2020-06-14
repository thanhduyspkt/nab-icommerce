/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.service.impl;

import com.nab.product.domain.Product;
import com.nab.product.dto.PriceChangedDTO;
import com.nab.product.repository.ProductRepository;
import com.nab.product.service.AuditService;
import com.nab.product.service.ProductService;
import com.nab.product.service.TrackingService;
import com.nab.product.service.exception.BadProductInfoException;
import com.nab.product.service.exception.DuplicatedProductException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private TrackingService trackingService;

    @Override
    public Product getById(long id) {
        auditService.auditProductDetailInfo(id);
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product insert(Product product) throws BadProductInfoException, DuplicatedProductException {
        log.info("insert: product={}", product);
        if (product == null) {
            throw new BadProductInfoException("product is null");
        }

        if (StringUtils.isEmpty(product.getName())) {
            throw new BadProductInfoException("name is null");
        }

        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new DuplicatedProductException("id: " + product.getId() + " is already existed!");
        }

        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void update(long id, Product product) throws ProductNotFoundException {
        log.info("update: id={}, product={}", id, product);
        final Product currentProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("id=" + id + " is not existed."));

        currentProduct.setName(product.getName());
        currentProduct.setBrand(product.getBrand());
        currentProduct.setColor(product.getColor());

        PriceChangedDTO priceChanged = null;

        if (product.getPrice() != null && !product.getPrice().equals(currentProduct.getPrice())) {
            priceChanged = new PriceChangedDTO(currentProduct.getId(), currentProduct.getName(),
                    currentProduct.getPrice(), product.getPrice());
            currentProduct.setPrice(product.getPrice());
        }

        productRepository.save(currentProduct);

        if (priceChanged != null) {
            // TODO: send message price changed
            trackingService.trackPriceChanged(priceChanged);
        }
    }

    @Transactional
    @Override
    public void decreaseQuantity(long id, int quantity) throws ProductNotFoundException, NotEnoughProductException {
        log.info("decreaseQuantity: id={}, quantity={}", id, quantity);
        final Product currentProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("id=" + id + " is not existed."));
        if (currentProduct.getQuantity() < quantity) {
            throw new NotEnoughProductException("It's only " + currentProduct.getQuantity() + " remaining");
        }
        currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
        productRepository.save(currentProduct);
        log.info("decreaseQuantity: id={}, quantity={}", id, quantity);
    }

    @Override
    public List<Product> search(SearchFilter filter, int page, int size) {
        log.info("search: filter={}, page={}, size={}", filter, page, size);
        auditService.auditSearchInfo(filter.getName(), filter.getPrice(), filter.getBrand(), filter.getColor());
        return productRepository.searchProduct(filter.getName(), filter.getPrice(), filter.getBrand(), filter.getColor(), page, size);
    }
}
