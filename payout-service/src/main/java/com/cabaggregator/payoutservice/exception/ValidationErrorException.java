package com.cabaggregator.payoutservice.exception;

import com.cabaggregator.payoutservice.core.constant.ErrorCauses;

public class ValidationErrorException extends ParameterizedException {
    public ValidationErrorException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.VALIDATION, messageKey, messageArgs);
    }
}
