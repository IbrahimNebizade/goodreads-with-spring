package com.company.goodreadsapp.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventHandler {
    private final AmqpTemplate amqpTemplate;

    @EventListener
    @Async
    public void handleMailEvent(MailSenderEvent event) {
        log.info("ActionLog.EventHandler.handleMailEvent.start");
        var mail = event.getMail();
        amqpTemplate.convertAndSend("send.mail", "route.mail.sender", mail);
        log.info("ActionLog.EventHandler.handleMailEvent.end");
    }
}
