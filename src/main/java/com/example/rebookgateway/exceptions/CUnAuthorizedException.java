package com.example.rebookgateway.exceptions;


public class CUnAuthorizedException extends RuntimeException{

    public CUnAuthorizedException() {
        super("Unauthorized");
    }
    public CUnAuthorizedException(String message) {
        super(message);
    }

    public CUnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
    public CUnAuthorizedException(Throwable cause) {
        super(cause);
    }
}
