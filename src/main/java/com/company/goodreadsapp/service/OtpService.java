package com.company.goodreadsapp.service;

import com.company.goodreadsapp.dto.CheckOtpCommand;
import com.company.goodreadsapp.dto.CreateOtpCommand;
import com.company.goodreadsapp.dto.CreateOtpResponse;
import com.company.goodreadsapp.dto.OtpCache;

public interface OtpService {
    CreateOtpResponse createOtp(CreateOtpCommand command);

    OtpCache checkOtp(CheckOtpCommand command);
}
