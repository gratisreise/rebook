package com.example.rebooknotificationservice.exception;

public class CUnauthorizedException extends RuntimeException {

    public CUnauthorizedException(String message) {
        super(message);
    }
    public CUnauthorizedException() {
        super();
    }
    public CUnauthorizedException(String message, Throwable cause) {super(message, cause);}
    public CUnauthorizedException(Throwable cause) {super(cause);}
}
