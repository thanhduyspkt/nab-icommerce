/**
 * Copyright (c) 2020 Absolute Software Corporation. All rights reserved. Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic, mechanical or
 * otherwise, is prohibited without the prior written consent of the copyright owner.
 */
package com.nab.audit.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

import lombok.Data;

@Document
@Data
public class AuditInfo {
    private String category;
    private Map<String, Object> info;
}
