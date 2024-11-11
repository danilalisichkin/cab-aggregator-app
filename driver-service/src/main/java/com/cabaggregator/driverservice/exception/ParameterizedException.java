package com.cabaggregator.driverservice.exception;

import lombok.Getter;

@Getter
public class ParameterizedException extends RuntimeException {
    private final String messageKey;
    private final Object[] messageArgs;

    public ParameterizedException(String messageKey, Object[] messageArgs) {
        super(messageKey);
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }
}