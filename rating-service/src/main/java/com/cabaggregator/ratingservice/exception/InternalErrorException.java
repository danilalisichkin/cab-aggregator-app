package com.cabaggregator.ratingservice.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(Throwable cause) {
        super(cause);
    }
}
