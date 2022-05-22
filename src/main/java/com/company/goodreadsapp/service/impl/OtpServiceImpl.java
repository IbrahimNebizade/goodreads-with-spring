package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.dto.CheckOtpCommand;
import com.company.goodreadsapp.dto.CreateOtpCommand;
import com.company.goodreadsapp.dto.CreateOtpResponse;
import com.company.goodreadsapp.dto.OtpCache;
import com.company.goodreadsapp.event.MailSenderEvent;
import com.company.goodreadsapp.exception.UnauthorizedException;
import com.company.goodreadsapp.model.Mail;
import com.company.goodreadsapp.service.OtpService;
import com.company.goodreadsapp.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.company.goodreadsapp.util.CacheKeys.OTP_BLOCK;
import static com.company.goodreadsapp.util.CacheKeys.OTP_CACHE;
import static com.company.goodreadsapp.util.CacheUtil.createKey;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {
    private final ApplicationEventPublisher eventPublisher;
    private final CacheUtil cache;

    @Override
    public CreateOtpResponse createOtp(CreateOtpCommand command) {
        var email = command.getEmail();
        var blockKey = createKey(OTP_BLOCK.name(), command.getEmail());
        if (!Objects.isNull(cache.get(blockKey))) {
            throw new UnauthorizedException("exception.auth.blocked.temporarily");
        }
        int randomPin = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomPin);
        eventPublisher.publishEvent(new MailSenderEvent(
                Mail.builder()
                        .to(email)
                        .subject("OTP code")
                        .html(true)
                        .template(new Mail.Template("otp.html",
                                Map.of("otpCode", otp)
                        ))
                        .build()
        ));
        var sessionId = UUID.randomUUID().toString();
        var otpCacheDto = OtpCache.builder()
                .otpCode(otp)
                .userId(command.getUserId())
                .email(email)
                .build();
        otpCacheDto.setExpiresAt();

        cache.set(createKey(OTP_CACHE.name(), sessionId), otpCacheDto, 3, TimeUnit.MINUTES);
        return CreateOtpResponse.builder()
                .expireInSec(180)
                .sessionId(sessionId)
                .build();
    }

    @Override
    public OtpCache checkOtp(CheckOtpCommand command) {
        var key = createKey(OTP_CACHE.name(), command.getSessionId());
        var otp = cache.<OtpCache>get(key);
        if (Objects.isNull(otp)) {
            throw new UnauthorizedException("exception.auth.otp.not-exist");
        }
        var isContain = otp.getOtpCode().equals(command.getCode());
        if (!isContain) {
            throw new UnauthorizedException("exception.auth.wrong-otp");
        }
        if (otp.getTries() >= 3) {
            var blockKey = createKey(OTP_BLOCK.name(), otp.getEmail());
            cache.set(blockKey, true, 10, TimeUnit.MINUTES);
            cache.delete(key);
            throw new UnauthorizedException("exception.auth.limit-exceeded");
        }
        if (otp.isExpired()) {
            cache.delete(key);
            throw new UnauthorizedException("exception.auth.expired-otp");
        }
        cache.delete(key);
        return otp;
    }
}
