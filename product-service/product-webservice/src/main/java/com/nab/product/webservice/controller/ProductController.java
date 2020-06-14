/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.webservice.controller;

import com.nab.product.domain.Product;
import com.nab.product.dto.ProductDTO;
import com.nab.product.service.ProductService;
import com.nab.product.service.exception.BadProductInfoException;
import com.nab.product.service.exception.DuplicatedProductException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable long id) {
        log.info("getProductById: id={}", id);
        final Product product = productService.getById(id);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return modelMapper.map(product, ProductDTO.class);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) throws BadProductInfoException, DuplicatedProductException {
        log.info("createProduct: productDTO={}", productDTO);
        final Product product = productService.insert(modelMapper.map(productDTO, Product.class));
        return modelMapper.map(product, ProductDTO.class);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        log.info("updateProduct: id={}, productDTO={}", id, productDTO);
        productService.update(id, modelMapper.map(productDTO, Product.class));
    }

    @Data
    static class SearchParams {
        private String name;
        private String brand;
        private String color;
        private Double price;
    }

    @GetMapping
    public List<ProductDTO> searchProducts(SearchParams params, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        log.info("searchProducts: params={}, page={}, size={}", params, page, size);
        if (page < 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        final List<Product> products = productService.search(modelMapper.map(params, ProductService.SearchFilter.class), page, size);
        return products.stream().map(p -> modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }
}
