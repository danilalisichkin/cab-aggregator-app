package com.cabaggregator.passengerservice.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(Throwable cause) {
        super(cause);
    }

    public InternalErrorException(String message) {
        super(message);
    }
}
