package com.company.goodreadsapp.exception;

public class BadCredentials extends BaseException{
    private static final String CODE = "exception.auth.bad-credentials";
    public BadCredentials() {
        super(CODE);
    }
}
