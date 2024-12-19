package com.cabaggregator.rideservice.exception;

import com.cabaggregator.rideservice.core.constant.ErrorCauses;

public class ValidationErrorException extends ParameterizedException {
    public ValidationErrorException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.VALIDATION, messageKey, messageArgs);
    }
}
