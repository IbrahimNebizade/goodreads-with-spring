package com.company.goodreadsapp.service;


import com.company.goodreadsapp.dto.SignInCommand;
import com.company.goodreadsapp.dto.SignInResponse;

public interface AuthService {
    SignInResponse signIn(SignInCommand command);
}
