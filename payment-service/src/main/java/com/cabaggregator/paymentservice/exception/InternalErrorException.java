package com.cabaggregator.paymentservice.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }

    public InternalErrorException(Throwable cause) {
        super(cause);
    }
}
