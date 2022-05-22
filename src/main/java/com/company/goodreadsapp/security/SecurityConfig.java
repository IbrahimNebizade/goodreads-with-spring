package com.company.goodreadsapp.security;

import com.company.goodreadsapp.dto.RestException;
import com.company.goodreadsapp.model.Role;
import com.company.goodreadsapp.security.filter.ExcludedFilter;
import com.company.goodreadsapp.security.filter.JwtAuthorizationFilter;
import com.company.goodreadsapp.security.filter.OtpFilter;
import com.company.goodreadsapp.security.filter.SignInFilter;
import com.company.goodreadsapp.security.provider.OtpProvider;
import com.company.goodreadsapp.security.provider.SignInProvider;
import com.company.goodreadsapp.util.MessageSourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SignInFilter signInFilter;
    private final SignInProvider signInProvider;
    private final OtpFilter otpFilter;
    private final OtpProvider otpProvider;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ObjectMapper mapper;
    private final MessageSourceUtil messageSourceUtil;

    private final Environment environment;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable csrf
        http.csrf().disable();

        // add cors
        http.cors().and();

        http.formLogin().disable();


        // disable session for jwt
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // add exception handling
        http.exceptionHandling()
                .accessDeniedHandler((request, response, e) -> {
                    response.getWriter()
                            .write(mapper.writeValueAsString(
                                    RestException.builder()
                                            .httpStatus(HttpStatus.FORBIDDEN)
                                            .code("exception.auth.access-denied")
                                            .message(messageSourceUtil.getMessage("exception.auth.access-denied"))
                                            .build()
                            ));
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                })
                .authenticationEntryPoint((request, response, e) -> {
                    response.getWriter()
                            .write(mapper.writeValueAsString(
                                    RestException.builder()
                                            .httpStatus(HttpStatus.UNAUTHORIZED)
                                            .code("exception.auth.unauthorized")
                                            .message(messageSourceUtil.getMessage("exception.auth.unauthorized"))
                                            .build()
                            ));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                });

        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
            http.authorizeRequests()
                    .anyRequest().permitAll();
        } else {
            // add request authorization
            http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/auth/otp/check").permitAll()
                    .antMatchers("/api/test/**").permitAll()
                    .antMatchers("/api/v1/admin/**").hasRole(Role.ADMIN.name())
                    .antMatchers(ExcludedFilter.convert()).permitAll()
                    .anyRequest().authenticated();
        }

        // add filter order for checking jwt
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(signInFilter, JwtAuthorizationFilter.class);
        http.addFilterAfter(otpFilter, SignInFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(signInProvider)
                .authenticationProvider(otpProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
