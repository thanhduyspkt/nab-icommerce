package com.nab.product.repository;

import com.nab.product.domain.Product;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    default List<Product> searchProduct(String name, Double price,String brand, String color, int page, int size) {
        final Product product = new Product();
        if (!StringUtils.isEmpty(name)) {
            product.setName(name);
        }

        if (price != null) {
            product.setPrice(price);
        }

        if (!StringUtils.isEmpty(brand)) {
            product.setBrand(brand);
        }

        if (!StringUtils.isEmpty(color)) {
            product.setColor(color);
        }

        return findAll(Example.of(product), PageRequest.of(page, size)).getContent();
    }
}
