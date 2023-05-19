package com.example.modulith.demo.notifications.management.application.events;

import java.time.OffsetDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.notifications.management.application.domain.Notification;
import com.example.modulith.demo.notifications.management.application.usecase.SendNotificationUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendNotificationCommandHandler {

    private final SendNotificationUseCase sendNotificationUseCase;

    @EventListener(classes = SendNotificationCommand.class)
    public void handle(SendNotificationCommand command) {
        sendNotificationUseCase.execute(new Notification(command.getUserId(), command.getText(), OffsetDateTime.now()));
    }
}
