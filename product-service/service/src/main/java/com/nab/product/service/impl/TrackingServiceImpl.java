/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.service.impl;

import com.nab.product.dto.PriceChangedDTO;
import com.nab.product.service.TrackingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrackingServiceImpl implements TrackingService {

    @Value("${app.topic.pricechanged}")
    private String productPriceChangedTopic;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void trackPriceChanged(PriceChangedDTO priceChangedDTO) {
        log.info("trackPriceChanged: {}", priceChangedDTO);
        jmsTemplate.convertAndSend(productPriceChangedTopic, priceChangedDTO);
    }
}
