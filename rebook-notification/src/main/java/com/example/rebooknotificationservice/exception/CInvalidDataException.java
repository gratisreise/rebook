package com.example.rebooknotificationservice.exception;

public class CInvalidDataException extends RuntimeException {

    public CInvalidDataException(String message) {
        super(message);
    }
    public CInvalidDataException(String message, Throwable cause) {
      super(message, cause);
    }
    public CInvalidDataException(Throwable cause) {
      super(cause);
    }
    public CInvalidDataException() {
      super();
    }
}
