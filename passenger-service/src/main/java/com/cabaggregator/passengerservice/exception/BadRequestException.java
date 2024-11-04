package com.cabaggregator.passengerservice.exception;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
