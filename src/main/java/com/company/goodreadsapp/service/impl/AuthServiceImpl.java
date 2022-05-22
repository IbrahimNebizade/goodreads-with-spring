package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.dto.CreateOtpCommand;
import com.company.goodreadsapp.dto.SignInCommand;
import com.company.goodreadsapp.dto.SignInResponse;
import com.company.goodreadsapp.exception.BadCredentials;
import com.company.goodreadsapp.exception.NotFoundException;
import com.company.goodreadsapp.repository.jpa.UserDetailsJpaRepository;
import com.company.goodreadsapp.service.AuthService;
import com.company.goodreadsapp.service.OtpService;
import com.company.goodreadsapp.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserDetailsJpaRepository userDetailsJpaRepository;
    private final OtpService otpService;

    @Override
    public SignInResponse signIn(SignInCommand command) {
        log.info("ActionLog.{}.signIn.start - command: {}", getClass().getSimpleName(), command);
        var loginDetail = userDetailsJpaRepository.findByEmail(command.getEmail())
                        .orElseThrow(()-> new NotFoundException("exception.auth.user.not-found"));
        if(!PasswordUtil.checkPassword(command.getPassword(), loginDetail.getPassword())){
            throw new BadCredentials();
        }
        var otpResponse = otpService.createOtp(CreateOtpCommand.builder()
                .email(command.getEmail())
                .userId(loginDetail.getUser().getId())
                .build());

        log.info("ActionLog.{}.signIn.end - command: {}", getClass().getSimpleName(), command);
        return new SignInResponse(otpResponse.getSessionId());

    }
}
