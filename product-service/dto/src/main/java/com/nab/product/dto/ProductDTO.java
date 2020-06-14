/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private Long storeId;

    private String name;
    private Double price;
    private String brand;
    private String color;
    private Integer quantity;
}
