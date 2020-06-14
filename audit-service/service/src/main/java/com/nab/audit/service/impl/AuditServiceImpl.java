/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.audit.service.impl;

import com.nab.audit.domain.AuditInfo;
import com.nab.audit.repository.AuditRepository;
import com.nab.audit.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void save(String category, Map<String, Object> info) {
        final AuditInfo auditInfo = new AuditInfo();
        auditInfo.setCategory(category);
        auditInfo.setInfo(info);
        auditRepository.insert(auditInfo);
    }
}
