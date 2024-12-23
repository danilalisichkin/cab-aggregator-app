package com.cabaggregator.paymentservice.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(Throwable cause) {
        super(cause);
    }
}
