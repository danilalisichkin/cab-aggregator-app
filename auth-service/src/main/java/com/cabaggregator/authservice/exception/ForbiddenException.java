package com.cabaggregator.authservice.exception;

import com.cabaggregator.authservice.core.constant.ErrorCauses;

public class ForbiddenException extends ParameterizedException {
    public ForbiddenException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.FORBIDDEN, messageKey, messageArgs);
    }
}
