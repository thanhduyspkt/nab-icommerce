/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.webservice.exception.handler;

import com.nab.product.service.exception.BadProductInfoException;
import com.nab.product.service.exception.DuplicatedProductException;
import com.nab.product.service.exception.NotEnoughProductException;
import com.nab.product.service.exception.ProductNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ProductExceptionHandler {
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({BadProductInfoException.class})
    public ResponseEntity<String> handleBadProductInfoException(BadProductInfoException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({NotEnoughProductException.class})
    public ResponseEntity<String> handleNotEnoughProductQuantityException(NotEnoughProductException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({DuplicatedProductException.class})
    public ResponseEntity<String> handleDuplicatedProductException(DuplicatedProductException e) {
        return error(CONFLICT, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
