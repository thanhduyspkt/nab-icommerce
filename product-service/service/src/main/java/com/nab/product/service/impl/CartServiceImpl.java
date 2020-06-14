/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.service.impl;

import com.nab.product.domain.Cart;
import com.nab.product.domain.CartProduct;
import com.nab.product.domain.Product;
import com.nab.product.repository.CartRepository;
import com.nab.product.service.CartService;
import com.nab.product.service.ProductService;
import com.nab.product.service.exception.CartNotFoundException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Cart getCartById(long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart createCart(long id) {
        final Cart cart = new Cart();
        cart.setId(id);
        return cartRepository.save(cart);
    }

    @Override
    public void addProduct(long id, long productId, int quantity) throws CartNotFoundException, ProductNotFoundException, NotEnoughProductException {
        final Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("id: " + id + " is not existed."));

        if (quantity == 0) {
            cart.getProducts().removeIf(p -> p.getProduct().getId() == productId);
            return;
        }

        final Product product = productService.getById(productId);
        if (product == null) {
            throw new ProductNotFoundException("productId: " + productId + " is not existed.");
        }

        if (product.getQuantity() < quantity) {
            throw new NotEnoughProductException("productId: " + productId + " only has " + product.getQuantity() + " items.");
        }

        cart.getProducts().removeIf(p -> p.getProduct().getId() == productId);
        final CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cart.getProducts().add(cartProduct);

        cartRepository.save(cart);
    }

    @Override
    public void emptyCart(long id) throws CartNotFoundException {
        final Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("id: " + id + " is not existed."));
        cart.getProducts().clear();
        cartRepository.save(cart);
    }

    @Override
    public boolean validateCart(long id) throws CartNotFoundException {
        final Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(""));

        if (CollectionUtils.isEmpty(cart.getProducts())) {
            return false;
        }

        for(CartProduct cartProduct: cart.getProducts()) {
            if (cartProduct.getProduct().getQuantity() < cartProduct.getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
