package com.cabaggregator.rideservice.exception;

public class ResourceNotFoundException extends ParameterizedException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
