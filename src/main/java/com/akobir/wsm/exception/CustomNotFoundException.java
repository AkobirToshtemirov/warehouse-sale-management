package com.akobir.wsm.exception;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String message) {
        super(message);
    }
}
