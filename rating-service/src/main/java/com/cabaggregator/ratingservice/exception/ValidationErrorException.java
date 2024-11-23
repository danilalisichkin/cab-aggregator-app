package com.cabaggregator.ratingservice.exception;

import com.cabaggregator.ratingservice.core.constant.ErrorCauses;

public class ValidationErrorException extends ParameterizedException {
    public ValidationErrorException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.VALIDATION, messageKey, messageArgs);
    }
}
