package com.company.goodreadsapp.security.filter;

import com.company.goodreadsapp.dto.CheckOtpCommand;
import com.company.goodreadsapp.dto.HeaderKeys;
import com.company.goodreadsapp.security.detail.OtpDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OtpFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/api/v1/auth/otp/check", "POST");

    private final ObjectMapper mapper;

    protected OtpFilter(@Lazy AuthenticationManager manager, ObjectMapper mapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, manager);
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException {
        var checkOtpCommand = mapper.readValue(request.getInputStream(), CheckOtpCommand.class);
        var token = new OtpToken(checkOtpCommand.getSessionId(), checkOtpCommand.getCode());
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        var tokenResponse = (OtpDetail) authResult.getPrincipal();
        response.setHeader(HeaderKeys.ACCESS_TOKEN, tokenResponse.getCreateTokenResponse().getAccessToken());
        response.setHeader(HeaderKeys.REFRESH_TOKEN, tokenResponse.getCreateTokenResponse().getRefreshToken());
        response.setStatus(HttpStatus.NO_CONTENT.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    public static class OtpToken extends UsernamePasswordAuthenticationToken {
        public OtpToken(Object principal, Object credentials) {
            super(principal, credentials);
        }

        @Override
        public Object getCredentials() {
            return super.getCredentials();
        }

        @Override
        public Object getPrincipal() {
            return super.getPrincipal();
        }
    }
}
