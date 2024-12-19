package com.cabaggregator.pricecalculationservice.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(Throwable cause) {
        super(cause);
    }

    public InternalErrorException(String message) {
        super(message);
    }
}