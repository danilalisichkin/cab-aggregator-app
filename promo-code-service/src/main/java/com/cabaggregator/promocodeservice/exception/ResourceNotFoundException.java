package com.cabaggregator.promocodeservice.exception;

import com.cabaggregator.promocodeservice.core.constant.ErrorCauses;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.NOT_FOUND, messageKey, messageArgs);
    }
}
