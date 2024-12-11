package com.cabaggregator.payoutservice.exception;

import com.cabaggregator.payoutservice.core.constant.ErrorCauses;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.BAD_REQUEST, messageKey, messageArgs);
    }
}
