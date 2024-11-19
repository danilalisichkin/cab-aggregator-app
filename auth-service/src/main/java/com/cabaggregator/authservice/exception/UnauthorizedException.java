package com.cabaggregator.authservice.exception;

import com.cabaggregator.authservice.core.constant.ErrorCauses;

public class UnauthorizedException extends ParameterizedException {
    public UnauthorizedException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.UNAUTHORIZED, messageKey, messageArgs);
    }
}
