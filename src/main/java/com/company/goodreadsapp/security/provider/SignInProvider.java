package com.company.goodreadsapp.security.provider;

import com.company.goodreadsapp.dto.SignInCommand;
import com.company.goodreadsapp.security.detail.SignInDetail;
import com.company.goodreadsapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignInProvider extends AbstractUserDetailsAuthenticationProvider {
    private final AuthService authService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        var response = authService.signIn(new SignInCommand(
                username,
                (String) authentication.getCredentials()
        ));
        log.debug("response: {}", response);

        return new SignInDetail(response.getSessionId());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class == authentication;
    }
}
