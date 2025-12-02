package com.example.rebookauthservice.exception;

public class AuthUserDataMissedException extends RuntimeException {

    public AuthUserDataMissedException() {
        super();
    }

    public AuthUserDataMissedException(String message) {
        super(message);
    }
}
