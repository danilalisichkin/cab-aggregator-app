package com.cabaggregator.passengerservice.exception;

import com.cabaggregator.passengerservice.core.constant.ErrorCauses;

public class ForbiddenException extends ParameterizedException {
    public ForbiddenException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.FORBIDDEN, messageKey, messageArgs);
    }
}
