/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.webservice.config;

import com.nab.product.domain.Product;
import com.nab.product.repository.ProductRepository;
import com.nab.product.service.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackageClasses = {
        ProductService.class
})
@EnableJpaRepositories(basePackageClasses = ProductRepository.class)
@EntityScan(basePackageClasses = Product.class)
public class ProductConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
