/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.service.impl;

import com.nab.tracking.domain.ProductPriceChanged;
import com.nab.tracking.repository.ProductPriceChangedRepository;
import com.nab.tracking.service.ProductPriceChangedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductPriceChangedServiceImpl implements ProductPriceChangedService {
    @Autowired
    private ProductPriceChangedRepository productPriceChangedRepository;

    @Override
    public ProductPriceChanged trackChange(Long productId, String name, Double oldPrice, Double newPrice) {
        ProductPriceChanged priceChanged = productPriceChangedRepository.findByProductId(productId);
        if (priceChanged == null) {
            priceChanged = new ProductPriceChanged();
            priceChanged.setProductId(productId);
            priceChanged.setPrices(new ArrayList<>());
        }

        priceChanged.setName(name);
        priceChanged.getPrices().add(new ProductPriceChanged.Prices(oldPrice, newPrice));
        productPriceChangedRepository.save(priceChanged);

        return priceChanged;
    }

    @Override
    public ProductPriceChanged getById(Long productId) {
        return productPriceChangedRepository.findByProductId(productId);
    }
}
