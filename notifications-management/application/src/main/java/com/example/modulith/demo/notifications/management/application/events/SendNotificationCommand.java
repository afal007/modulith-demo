package com.example.modulith.demo.notifications.management.application.events;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public final class SendNotificationCommand extends ApplicationEvent {

    private final UUID userId;
    private final String text;

    public SendNotificationCommand(Object source, UUID userId, String text) {
        super(source);
        this.text = text;
        this.userId = userId;
    }
}
