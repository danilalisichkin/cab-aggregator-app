package com.cabaggregator.ratingservice.exception;

import com.cabaggregator.ratingservice.core.constant.ErrorCauses;

public class ForbiddenException extends ParameterizedException {
    public ForbiddenException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.FORBIDDEN, messageKey, messageArgs);
    }
}
