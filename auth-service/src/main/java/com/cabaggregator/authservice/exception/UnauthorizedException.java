package com.cabaggregator.authservice.exception;

public class UnauthorizedException extends ParameterizedException {
    public UnauthorizedException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
