package com.example.restapi.advice.exception;

// prefix C : custom
public class CUserNotFoundException extends RuntimeException {
    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Controller 에서 메시지를 받아 예외 처리 시 사용이 필요하면 CUserNotFoundException(String msg)을 사용
    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException() {
        super();
    }
}
