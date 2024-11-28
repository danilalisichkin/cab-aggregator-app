package com.cabaggregator.ratingservice.exception;

import com.cabaggregator.ratingservice.core.constant.ErrorCauses;

public class DataUniquenessConflictException extends ParameterizedException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.UNIQUENESS_CONFLICT, messageKey, messageArgs);
    }
}
