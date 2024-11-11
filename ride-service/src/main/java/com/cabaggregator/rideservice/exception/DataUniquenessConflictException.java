package com.cabaggregator.rideservice.exception;

public class DataUniquenessConflictException extends ParameterizedException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
