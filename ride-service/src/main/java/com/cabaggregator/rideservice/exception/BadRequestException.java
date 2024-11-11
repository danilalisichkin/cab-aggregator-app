package com.cabaggregator.rideservice.exception;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
