/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.tracking.product.price;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.product.dto.PriceChangedDTO;
import com.nab.tracking.service.ProductPriceChangedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import sun.dc.pr.PRError;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductPriceChangedListener {
    @Autowired
    private ProductPriceChangedService productPriceChangedService;

    @JmsListener(destination = "product.price.changed", containerFactory = "connectionFactory")
    public void receive(Message jsonMessage) throws JMSException, IOException {
        log.info("receive: {}", jsonMessage);

        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            String messageData = textMessage.getText();
            ObjectMapper mapper = new ObjectMapper();
            final PriceChangedDTO message = mapper.readValue(messageData, PriceChangedDTO.class);
            productPriceChangedService.trackChange(message.getProductId(), message.getName(), message.getOldPrice(), message.getNewPrice());
        }
    }
}
