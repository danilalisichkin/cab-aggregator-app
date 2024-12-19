package com.cabaggregator.rideservice.exception;

import com.cabaggregator.rideservice.core.constant.ErrorCauses;

public class DataUniquenessConflictException extends ParameterizedException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.UNIQUENESS_CONFLICT, messageKey, messageArgs);
    }
}
