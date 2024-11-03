package com.cabaggregator.driverservice.exception;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
