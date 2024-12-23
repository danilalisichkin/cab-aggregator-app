package com.cabaggregator.rideservice.exception;

import com.cabaggregator.rideservice.core.constant.ErrorCauses;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.NOT_FOUND, messageKey, messageArgs);
    }
}
