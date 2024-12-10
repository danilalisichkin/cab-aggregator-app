package com.cabaggregator.payoutservice.exception;

import com.cabaggregator.payoutservice.core.constant.ErrorCauses;

public class ForbiddenException extends ParameterizedException {
    public ForbiddenException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.FORBIDDEN, messageKey, messageArgs);
    }
}
