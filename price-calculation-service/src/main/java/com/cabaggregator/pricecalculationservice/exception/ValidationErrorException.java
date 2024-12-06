package com.cabaggregator.pricecalculationservice.exception;

import com.cabaggregator.pricecalculationservice.core.constant.ErrorCauses;

public class ValidationErrorException extends ParameterizedException {
    public ValidationErrorException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.VALIDATION, messageKey, messageArgs);
    }
}
