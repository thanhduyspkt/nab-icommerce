/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.product.service.exception;

public class NotEnoughProductException extends Exception {
    public NotEnoughProductException(String msg) {
        super(msg);
    }
}
