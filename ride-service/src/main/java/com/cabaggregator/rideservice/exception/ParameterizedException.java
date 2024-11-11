package com.cabaggregator.rideservice.exception;

import lombok.Getter;

@Getter
public class ParameterizedException extends RuntimeException {
    private final String messageKey;
    private final Object[] messageArgs;

    public ParameterizedException(String messageKey, Object... messageArgs) {
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }
}
