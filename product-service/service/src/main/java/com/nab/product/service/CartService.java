package com.nab.product.service;

import com.nab.product.domain.Cart;
import com.nab.product.service.exception.CartNotFoundException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.ProductNotFoundException;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface CartService {
    Cart getCartById(long id);
    Cart createCart(long id);
    void addProduct(long id, long productId, int quantity) throws CartNotFoundException, ProductNotFoundException, NotEnoughProductException;
    void emptyCart(long id) throws CartNotFoundException;
    boolean validateCart(long id) throws CartNotFoundException;
}
