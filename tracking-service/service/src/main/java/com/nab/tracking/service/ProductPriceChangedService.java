/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.service;

import com.nab.tracking.domain.ProductPriceChanged;

public interface ProductPriceChangedService {
    ProductPriceChanged trackChange(Long productId, String name, Double oldPrice, Double newPrice);
    ProductPriceChanged getById(Long productId);
}
