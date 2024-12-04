package com.cabaggregator.paymentservice.exception;

import com.cabaggregator.paymentservice.core.constant.ErrorCauses;

public class ForbiddenException extends ParameterizedException {
    public ForbiddenException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.FORBIDDEN, messageKey, messageArgs);
    }
}
