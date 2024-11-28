package com.cabaggregator.promocodeservice.exception;

import com.cabaggregator.promocodeservice.core.constant.ErrorCauses;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.BAD_REQUEST, messageKey, messageArgs);
    }
}
