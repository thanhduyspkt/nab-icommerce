package com.nab.audit.repository;

import com.nab.audit.domain.AuditInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
public interface AuditRepository extends MongoRepository<AuditInfo, String> {
}
