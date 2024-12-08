package com.cabaggregator.passengerservice.exception;

import com.cabaggregator.passengerservice.core.constant.ErrorCauses;

public class ValidationErrorException extends ParameterizedException {
    public ValidationErrorException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.VALIDATION, messageKey, messageArgs);
    }
}
