package com.nab.product.repository;

import com.nab.product.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
