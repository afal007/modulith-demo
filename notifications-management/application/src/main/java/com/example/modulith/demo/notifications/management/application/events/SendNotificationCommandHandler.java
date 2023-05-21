package com.example.modulith.demo.notifications.management.application.events;

import java.time.OffsetDateTime;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.notifications.management.application.domain.Notification;
import com.example.modulith.demo.notifications.management.application.usecase.SendNotificationUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendNotificationCommandHandler {

    private final SendNotificationUseCase sendNotificationUseCase;

    @CommandHandler
    public void handle(SendNotificationCommand command) {
        sendNotificationUseCase.execute(new Notification(command.userId(), command.text(), OffsetDateTime.now()));
    }
}
