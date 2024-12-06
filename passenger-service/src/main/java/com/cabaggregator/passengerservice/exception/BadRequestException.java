package com.cabaggregator.passengerservice.exception;

import com.cabaggregator.passengerservice.core.constant.ErrorCauses;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.BAD_REQUEST, messageKey, messageArgs);
    }
}
