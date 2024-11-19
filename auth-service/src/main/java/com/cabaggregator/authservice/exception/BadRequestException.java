package com.cabaggregator.authservice.exception;

public class BadRequestException extends ParameterizedException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
