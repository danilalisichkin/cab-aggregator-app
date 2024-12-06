package com.cabaggregator.pricecalculationservice.exception;

import com.cabaggregator.pricecalculationservice.core.constant.ErrorCauses;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.BAD_REQUEST, messageKey, messageArgs);
    }
}
