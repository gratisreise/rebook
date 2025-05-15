package com.example.rebookgateway.exceptions;

public class CDuplicatedException extends RuntimeException {

    public CDuplicatedException(String message) {
        super(message);
    }

    public CDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
    public CDuplicatedException(Throwable cause) {
        super(cause);
    }
}
