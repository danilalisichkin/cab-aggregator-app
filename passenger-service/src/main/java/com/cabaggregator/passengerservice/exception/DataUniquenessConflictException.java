package com.cabaggregator.passengerservice.exception;

import com.cabaggregator.passengerservice.core.constant.ErrorCauses;

public class DataUniquenessConflictException extends ParameterizedException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.UNIQUENESS_CONFLICT, messageKey, messageArgs);
    }
}
