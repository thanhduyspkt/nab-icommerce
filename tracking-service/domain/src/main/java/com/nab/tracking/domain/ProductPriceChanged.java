/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
public class ProductPriceChanged {

    @AllArgsConstructor
    @Data
    public static class Prices {
        private double oldPrice;
        private double newPrice;
    }

    @Id
    private String id;
    private Long productId;
    private String name;
    private List<Prices> prices;
}
