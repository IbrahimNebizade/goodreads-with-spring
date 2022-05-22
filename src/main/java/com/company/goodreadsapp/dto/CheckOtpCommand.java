package com.company.goodreadsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOtpCommand {
    private String sessionId;
    private String code;
}
