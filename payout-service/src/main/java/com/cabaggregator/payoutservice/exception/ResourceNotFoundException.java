package com.cabaggregator.payoutservice.exception;

import com.cabaggregator.payoutservice.core.constant.ErrorCauses;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.NOT_FOUND, messageKey, messageArgs);
    }
}
