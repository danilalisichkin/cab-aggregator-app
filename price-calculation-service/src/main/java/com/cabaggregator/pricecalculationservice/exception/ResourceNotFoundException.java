package com.cabaggregator.pricecalculationservice.exception;

import com.cabaggregator.pricecalculationservice.core.constant.ErrorCauses;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.NOT_FOUND, messageKey, messageArgs);
    }
}
