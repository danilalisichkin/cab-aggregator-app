package com.cabaggregator.authservice.exception;

public class ForbiddenException extends ParameterizedException {
    public ForbiddenException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
