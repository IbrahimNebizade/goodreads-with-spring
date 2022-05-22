package com.company.goodreadsapp.exception;

public class BaseException extends RuntimeException {
    public final String code;
    public String message;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(String code) {
        super(code);
        this.code = code;
    }
}
