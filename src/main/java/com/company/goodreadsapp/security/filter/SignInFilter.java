package com.company.goodreadsapp.security.filter;

import com.company.goodreadsapp.dto.SignInCommand;
import com.company.goodreadsapp.dto.SignInResponse;
import com.company.goodreadsapp.security.detail.SignInDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SignInFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/auth",
            "POST");
    private final ObjectMapper mapper;

    protected SignInFilter(@Lazy AuthenticationManager manager, ObjectMapper mapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, manager);
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        var command = mapper.readValue(request.getInputStream(), SignInCommand.class);
        var token = new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        log.debug("authResult: {}", authResult);
        var customResult = (SignInDetail) authResult.getPrincipal();
        response.getWriter().write(mapper.writeValueAsString(
                new SignInResponse(customResult.getSessionId())
        ));
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}
