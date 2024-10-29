package com.cabaggregator.driverservice.exceptions;

public class DataUniquenessConflictException extends ServerErrorException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
