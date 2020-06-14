/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.webservice.controller;

import com.nab.tracking.domain.ProductPriceChanged;
import com.nab.tracking.dto.ProductPriceChangedDTO;
import com.nab.tracking.service.ProductPriceChangedService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/product-prices")
public class ProductPriceChangedController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductPriceChangedService productPriceChangedService;

    @GetMapping("/{productId}")
    public ProductPriceChangedDTO getProductPriceChanged(@PathVariable Long productId) {
        final ProductPriceChanged productPriceChanged = productPriceChangedService.getById(productId);

        if (productPriceChanged == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return modelMapper.map(productPriceChanged, ProductPriceChangedDTO.class);
    }
}
