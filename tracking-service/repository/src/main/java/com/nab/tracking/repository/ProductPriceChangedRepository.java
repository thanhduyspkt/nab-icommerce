/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.repository;

import com.nab.tracking.domain.ProductPriceChanged;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductPriceChangedRepository extends MongoRepository<ProductPriceChanged, String> {
    @Query(value = "{productId: ?0}")
    ProductPriceChanged findByProductId(Long id);
}
