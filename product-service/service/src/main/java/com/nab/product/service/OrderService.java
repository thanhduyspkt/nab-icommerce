package com.nab.product.service;

import com.nab.product.domain.Order;
import com.nab.product.service.exception.InvalidCartException;
import com.nab.product.service.exception.CartNotFoundException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.OrderNotFoundException;
import com.nab.product.service.exception.ProductNotFoundException;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface OrderService {
    Order getOrderById(long id, long userId) throws OrderNotFoundException;
    Order createOrder(long userId) throws CartNotFoundException, InvalidCartException, NotEnoughProductException, ProductNotFoundException;
    Order updateOrderStatus(long id, long userId, int status) throws OrderNotFoundException;
}
