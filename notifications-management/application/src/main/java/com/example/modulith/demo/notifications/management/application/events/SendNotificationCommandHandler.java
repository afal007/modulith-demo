package com.example.modulith.demo.notifications.management.application.events;

import java.time.OffsetDateTime;

import org.axonframework.commandhandling.CommandHandler;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.stereotype.Component;

import com.example.modulith.demo.notifications.management.api.command.SendNotificationIntegrationCommand;
import com.example.modulith.demo.notifications.management.application.domain.Notification;
import com.example.modulith.demo.notifications.management.application.usecase.SendNotificationUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendNotificationCommandHandler {

    private final SendNotificationUseCase sendNotificationUseCase;

    @CommandHandler
    @DomainEventHandler
    public void handle(SendNotificationIntegrationCommand command) {
        LOGGER.info("Команда на отправку нотификации {}", command);
        sendNotificationUseCase.execute(new Notification(command.userId(), command.text(), OffsetDateTime.now()));
    }
}
