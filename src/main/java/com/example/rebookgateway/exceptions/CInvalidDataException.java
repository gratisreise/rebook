package com.example.rebookgateway.exceptions;

public class CInvalidDataException extends RuntimeException {

    public CInvalidDataException() {
        super();
    }
    public CInvalidDataException(String message) {
        super(message);
    }
    public CInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
