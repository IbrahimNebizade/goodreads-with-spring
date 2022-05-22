package com.company.goodreadsapp.service;

import com.company.goodreadsapp.dto.CreateTokenCommand;
import com.company.goodreadsapp.dto.CreateTokenResponse;

public interface TokenService {
    CreateTokenResponse createToken(CreateTokenCommand command);
}
