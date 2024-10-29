package com.cabaggregator.driverservice.exceptions;

import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {
    private final String messageKey;
    private final Object[] messageArgs;

    public ServerErrorException(String messageKey, Object[] messageArgs) {
        super(messageKey);
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }
}
