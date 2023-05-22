package com.example.modulith.demo.notifications.management.api.command;

import java.util.UUID;

public record SendNotificationIntegrationCommand(UUID userId, String text) {}
