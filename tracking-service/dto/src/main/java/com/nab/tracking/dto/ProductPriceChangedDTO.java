/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductPriceChangedDTO {

    @Data
    public static class Prices {
        private double oldPrice;
        private double newPrice;
    }

    private Long productId;
    private String name;
    private List<Prices> prices;
}
