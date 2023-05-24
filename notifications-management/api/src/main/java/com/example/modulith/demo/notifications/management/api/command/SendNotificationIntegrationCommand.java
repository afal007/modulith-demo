package com.example.modulith.demo.notifications.management.api.command;

import java.util.UUID;

import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record SendNotificationIntegrationCommand(UUID userId, String text) {}
