package com.nab.product.service;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface AuditService {
    void auditSearchInfo(String name, Double price, String brand, String color);
    void auditProductDetailInfo(Long productId);
}
