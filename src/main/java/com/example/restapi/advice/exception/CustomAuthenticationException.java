package com.example.restapi.advice.exception;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException() {
        super();
    }

    public CustomAuthenticationException(String message) {
        super(message);
    }

    public CustomAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
