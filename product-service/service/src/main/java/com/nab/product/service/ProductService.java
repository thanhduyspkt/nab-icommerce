package com.nab.product.service;

import com.nab.product.domain.Product;
import com.nab.product.service.exception.BadProductInfoException;
import com.nab.product.service.exception.DuplicatedProductException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.ProductNotFoundException;

import java.util.List;

import lombok.Data;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface ProductService {

    @Data
    class SearchFilter {
        private String name;
        private Double price;
        private String brand;
        private String color;
    }

    Product getById(long id);
    Product insert(Product product) throws BadProductInfoException, DuplicatedProductException;
    void update(long id, Product product) throws ProductNotFoundException;
    void decreaseQuantity(long id, int quantity) throws ProductNotFoundException, NotEnoughProductException;
    List<Product> search(SearchFilter filter, int page, int size);
}
