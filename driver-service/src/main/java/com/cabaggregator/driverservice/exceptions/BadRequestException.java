package com.cabaggregator.driverservice.exceptions;

public class BadRequestException extends ServerErrorException {
    public BadRequestException(String messageKey, Object... messageArgs) {
        super(messageKey, messageArgs);
    }
}
