package com.company.goodreadsapp.security.filter;

import com.company.goodreadsapp.dto.TokenType;
import com.company.goodreadsapp.dto.User;
import com.company.goodreadsapp.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import static com.company.goodreadsapp.dto.HeaderKeys.USER_ID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            var header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
                var token = header.split(" ")[1].trim();
                var claim = jwtUtil.validate(token, TokenType.ACCESS);

                log.debug("claim: {}", claim);

                setHeaders(request, claim);
                var user = mapper.convertValue(claim.getClaim("user"), User.class);

                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(
                                user.getId(),
                                token,
                                user.getGrants()
                                        .stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList())
                        ));
            }
        } catch (Exception e) {
            log.error("error: ", e);
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setHeaders(HttpServletRequest request, JWTClaimsSet claim) {
        request.setAttribute(USER_ID, claim.getSubject());
    }
}
