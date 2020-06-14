/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.service.impl;

import com.nab.product.dto.AuditDTO;
import com.nab.product.service.AuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuditServiceImpl implements AuditService {
    @Value("${app.topic.audit}")
    private String auditTopic;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void auditSearchInfo(String name, Double price, String brand, String color) {
        final AuditDTO auditDTO = new AuditDTO();
        auditDTO.setCategory("search");
        final HashMap<String, Object> info = new HashMap<>();
        info.put("name", name);
        info.put("price", price);
        info.put("brand", brand);
        info.put("color", color);
        auditDTO.setInfo(info);
        jmsTemplate.convertAndSend(auditTopic, auditDTO);
    }

    @Override
    public void auditProductDetailInfo(Long productId) {
        final AuditDTO auditDTO = new AuditDTO();
        auditDTO.setCategory("productDetail");
        final HashMap<String, Object> info = new HashMap<>();
        info.put("productId", productId);
        auditDTO.setInfo(info);
        jmsTemplate.convertAndSend(auditTopic, auditDTO);
    }
}
