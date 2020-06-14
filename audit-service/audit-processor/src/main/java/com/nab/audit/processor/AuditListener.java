/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.audit.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.audit.service.AuditService;
import com.nab.product.dto.AuditDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuditListener {

    @Autowired
    private AuditService auditService;

    @JmsListener(destination = "audit", containerFactory = "connectionFactory")
    public void receive(Message jsonMessage) throws JMSException, IOException {
        log.info("receive: {}", jsonMessage);

        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            String messageData = textMessage.getText();
            ObjectMapper mapper = new ObjectMapper();
            final AuditDTO auditDTO = mapper.readValue(messageData, AuditDTO.class);
            auditService.save(auditDTO.getCategory(), auditDTO.getInfo());
        }
    }
}
