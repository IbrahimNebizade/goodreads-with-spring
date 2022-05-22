package com.company.goodreadsapp.event;

import com.company.goodreadsapp.model.Mail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MailSenderEvent {
    private final Mail mail;
}
