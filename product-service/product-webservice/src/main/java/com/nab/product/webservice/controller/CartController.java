/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.webservice.controller;

import com.nab.product.domain.Cart;
import com.nab.product.dto.AddProductToCartRequest;
import com.nab.product.dto.CartDTO;
import com.nab.product.dto.CartProductDTO;
import com.nab.product.service.CartService;
import com.nab.product.service.exception.CartNotFoundException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public void createCart(@RequestHeader Long userId) {
        cartService.createCart(userId);
    }

    @PostMapping("/add-product")
    public void addProduct(@RequestHeader Long userId, @RequestBody AddProductToCartRequest addProductToCartRequest) throws NotEnoughProductException, CartNotFoundException, ProductNotFoundException {
        cartService.addProduct(userId, addProductToCartRequest.getProductId(), addProductToCartRequest.getQuantity());
    }

    @PostMapping("/clear")
    public void clearCart(@RequestHeader Long userId) throws CartNotFoundException {
        cartService.emptyCart(userId);
    }

    @GetMapping
    public CartDTO getCart(@RequestHeader Long userId) {
        final Cart cart = cartService.getCartById(userId);

        if (cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        final CartDTO cartDTO = new CartDTO();
        final ArrayList<CartProductDTO> cartProducts = new ArrayList<>();
        cartDTO.setId(cart.getId());
        cart.getProducts().forEach(p -> {
            final CartProductDTO cartProduct = new CartProductDTO();
            cartProduct.setProductId(p.getProduct().getId());
            cartProduct.setName(p.getProduct().getName());
            cartProduct.setQuantity(p.getQuantity());
            cartProducts.add(cartProduct);
        });
        cartDTO.setProducts(cartProducts);
        return cartDTO;
    }
}
