package com.cabaggregator.ratingservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParameterizedException extends RuntimeException {
    String errorCauseKey;
    String messageKey;
    Object[] messageArgs;

    public ParameterizedException(String errorCauseKey, String messageKey, Object[] messageArgs) {
        super(messageKey);
        this.errorCauseKey = errorCauseKey;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }
}
