/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductionWSApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductionWSApplication.class, args);
    }
}
