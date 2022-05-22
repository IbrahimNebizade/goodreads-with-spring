package com.company.goodreadsapp.util;

import com.company.goodreadsapp.dto.TokenType;
import com.company.goodreadsapp.dto.User;
import com.company.goodreadsapp.exception.UnauthorizedException;
import com.company.goodreadsapp.model.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    public JWTClaimsSet validate(String token, TokenType tokenType) {
        log.info("ActionLog.JwtUtil.validate.start");
        try {
            var signedJwt = SignedJWT.parse(token);
            var verifier = new MACVerifier(secretKey.getBytes());

            if (!signedJwt.verify(verifier)) {
                throw new UnauthorizedException("exception.auth.token.invalid");
            }
            if (new Date().after(signedJwt.getJWTClaimsSet().getExpirationTime())) {
                throw new UnauthorizedException("exception.auth.token.expired");
            }
            if (!signedJwt.getJWTClaimsSet().getClaim("tokenType").equals(tokenType.name())) {
                throw new UnauthorizedException("exception.auth.token.wrong-type");
            }
            log.info("ActionLog.JwtUtil.validate.end");
            return signedJwt.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            log.error("jwt token exception: ", e);
            throw new UnauthorizedException("exception.auth.unauthorized");
        } catch (Exception e) {
            log.error("exception: ", e);
            throw e;
        }
    }

    public String generateToken(Long userId,
                                String username,
                                List<Role> roles,
                                Integer expirationInMinutes,
                                TokenType tokenType) {
        try {
            var signer = new MACSigner(secretKey.getBytes());
            var issueTime = new Date();
            var expirationTime = new Date(issueTime.getTime() + expirationInMinutes * 60 * 1000);
            List<String> grants = new LinkedList<>();
            for (Role role : roles) {
                grants.add(role.getRole());
                grants.addAll(role.getPermissions());
            }

            var claimsSet = new JWTClaimsSet.Builder()
                    .subject(userId.toString())
                    .issuer(this.issuer)
                    .issueTime(issueTime)
                    .expirationTime(expirationTime)
                    .claim("tokenType", tokenType)
                    .claim("user", new User(grants, userId, username))
                    .build();

            var signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new UnauthorizedException("exception.auth.token.not-generated");
        }
    }
}
