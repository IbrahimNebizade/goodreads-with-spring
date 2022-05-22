package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.dto.CreateTokenCommand;
import com.company.goodreadsapp.dto.CreateTokenResponse;
import com.company.goodreadsapp.dto.TokenType;
import com.company.goodreadsapp.model.Role;
import com.company.goodreadsapp.service.TokenService;
import com.company.goodreadsapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final JwtUtil jwtUtil;

    @Value("${jwt.token.access.expireInMinutes}")
    private Integer accessTokenExpiration;

    @Value("${jwt.token.refresh.expireInMinutes}")
    private Integer refreshTokenExpiration;

    @Override
    public CreateTokenResponse createToken(CreateTokenCommand command) {
        log.info("ActionLog.TokenServiceImpl.createToken.start - userId: {}", command.getUserId());
        var accessToken = jwtUtil.generateToken(command.getUserId(),
                command.getUsername(),
                List.of(Role.ADMIN),
                accessTokenExpiration,
                TokenType.ACCESS
        );
        var refreshToken = jwtUtil.generateToken(command.getUserId(),
                command.getUsername(),
                List.of(Role.ADMIN),
                refreshTokenExpiration,
                TokenType.REFRESH
        );
        log.info("ActionLog.TokenServiceImpl.createToken.end - userId: {}", command.getUserId());
        return new CreateTokenResponse(accessToken, refreshToken);
    }
}
