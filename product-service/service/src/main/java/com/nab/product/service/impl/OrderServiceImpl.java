/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.service.impl;

import com.nab.product.domain.Cart;
import com.nab.product.domain.CartProduct;
import com.nab.product.domain.Order;
import com.nab.product.domain.OrderProduct;
import com.nab.product.repository.OrderRepository;
import com.nab.product.service.CartService;
import com.nab.product.service.OrderService;
import com.nab.product.service.ProductService;
import com.nab.product.service.exception.InvalidCartException;
import com.nab.product.service.exception.CartNotFoundException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.OrderNotFoundException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Order getOrderById(long id, long userId) throws OrderNotFoundException {
        final Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }

        if (order.getUserId() != userId) {
            throw new OrderNotFoundException("");
        }

        return order;
    }

    @Override
    public Order createOrder(long userId) throws CartNotFoundException, InvalidCartException, NotEnoughProductException, ProductNotFoundException {
        if (!cartService.validateCart(userId)) {
            throw new InvalidCartException("cartId: " + userId + " is empty");
        }

        final Cart cart = cartService.getCartById(userId);

        final Order order = new Order();
        order.setStatus(0);
        order.setUserId(userId);
        final ArrayList<OrderProduct> products = new ArrayList<>();
        order.setProducts(products);

        for (CartProduct p: cart.getProducts()) {
            final OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(p.getProduct());
            orderProduct.setQuantity(p.getQuantity());
            orderProduct.setPrice(p.getProduct().getPrice());
            order.getProducts().add(orderProduct);
            productService.decreaseQuantity(p.getProduct().getId(), p.getQuantity());
        }

        cartService.emptyCart(userId);

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(long id, long userId, int status) throws OrderNotFoundException {
        // TODO: check user permission on the status

        final Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("orderId: " + id + " is not existed."));

        if (order.getUserId() != userId) {
            throw new OrderNotFoundException("");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }
}
