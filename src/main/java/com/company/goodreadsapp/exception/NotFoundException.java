package com.company.goodreadsapp.exception;

public class NotFoundException extends BaseException{
    public NotFoundException(String code, String message) {
        super(code, message);
    }

    public NotFoundException(String code) {
        super(code);
    }
}
