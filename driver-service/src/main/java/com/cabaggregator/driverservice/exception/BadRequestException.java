package com.cabaggregator.driverservice.exception;

import com.cabaggregator.driverservice.core.constant.ErrorCauses;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.BAD_REQUEST, messageKey, messageArgs);
    }
}
