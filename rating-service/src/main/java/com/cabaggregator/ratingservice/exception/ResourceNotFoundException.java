package com.cabaggregator.ratingservice.exception;

import com.cabaggregator.ratingservice.core.constant.ErrorCauses;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.NOT_FOUND, messageKey, messageArgs);
    }
}
