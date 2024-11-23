package com.cabaggregator.ratingservice.exception;

import lombok.Getter;

@Getter
public class ParameterizedException extends RuntimeException {
    private final String errorCauseKey;
    private final String messageKey;
    private final Object[] messageArgs;

    public ParameterizedException(String errorCauseKey, String messageKey, Object[] messageArgs) {
        super(messageKey);
        this.errorCauseKey = errorCauseKey;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }
}
