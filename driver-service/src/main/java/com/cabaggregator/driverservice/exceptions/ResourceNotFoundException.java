package com.cabaggregator.driverservice.exceptions;

public class ResourceNotFoundException extends ServerErrorException {
    public ResourceNotFoundException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
