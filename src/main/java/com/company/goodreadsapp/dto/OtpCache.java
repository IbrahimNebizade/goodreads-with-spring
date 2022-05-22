package com.company.goodreadsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpCache {
    private Long userId;
    private String email;
    private String otpCode;
    @Builder.Default
    private Integer tries = 0;
    private Instant expiresAt;

    public void setExpiresAt() {
        this.expiresAt = Instant.now().plus(3, ChronoUnit.MINUTES);
    }

    public boolean isExpired() {
        return this.expiresAt.isBefore(Instant.now());
    }
}
