package com.company.goodreadsapp.security.provider;


import com.company.goodreadsapp.dto.CheckOtpCommand;
import com.company.goodreadsapp.dto.CreateTokenCommand;
import com.company.goodreadsapp.security.detail.OtpDetail;
import com.company.goodreadsapp.security.filter.OtpFilter;
import com.company.goodreadsapp.service.OtpService;
import com.company.goodreadsapp.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpProvider extends AbstractUserDetailsAuthenticationProvider {
    private final OtpService otpService;
    private final TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        var otpCache = otpService.checkOtp(new CheckOtpCommand(username, (String) authentication.getCredentials()));

        var tokenResponse = tokenService.createToken(new CreateTokenCommand(otpCache.getUserId(), otpCache.getEmail()));
        return new OtpDetail(tokenResponse);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpFilter.OtpToken.class == authentication;
    }
}
