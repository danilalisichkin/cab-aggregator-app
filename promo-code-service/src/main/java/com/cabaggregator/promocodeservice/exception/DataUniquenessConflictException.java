package com.cabaggregator.promocodeservice.exception;

import com.cabaggregator.promocodeservice.core.constant.ErrorCauses;

public class DataUniquenessConflictException extends ParameterizedException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.UNIQUENESS_CONFLICT, messageKey, messageArgs);
    }
}
