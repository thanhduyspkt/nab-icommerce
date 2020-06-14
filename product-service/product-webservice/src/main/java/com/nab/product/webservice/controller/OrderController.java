/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.webservice.controller;

import com.nab.product.domain.Order;
import com.nab.product.dto.OrderDTO;
import com.nab.product.dto.OrderProductDTO;
import com.nab.product.dto.UpdateOrderStatusRequest;
import com.nab.product.service.OrderService;
import com.nab.product.service.exception.CartNotFoundException;
import com.nab.product.service.exception.InvalidCartException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.OrderNotFoundException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.modelmapper.ModelMapper;
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

import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id, @RequestHeader Long userId) throws OrderNotFoundException {
        final Order order = orderService.getOrderById(id, userId);

        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return toDTO(order);
    }

    @PostMapping("/create")
    public OrderDTO createOrder(@RequestHeader Long userId) throws InvalidCartException, CartNotFoundException, NotEnoughProductException, ProductNotFoundException {
        final Order order = orderService.createOrder(userId);
        return toDTO(order);
    }

    @PostMapping("/update-status")
    public OrderDTO updateOrderStatus(@RequestHeader Long userId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) throws OrderNotFoundException {
        final Order order = orderService.updateOrderStatus(updateOrderStatusRequest.getId(), userId, updateOrderStatusRequest.getStatus());
        return toDTO(order);
    }

    public OrderDTO toDTO(Order order) {
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setProducts(order.getProducts().stream().map(p -> modelMapper.map(p, OrderProductDTO.class)).collect(Collectors.toList()));
        return orderDTO;
    }
}
