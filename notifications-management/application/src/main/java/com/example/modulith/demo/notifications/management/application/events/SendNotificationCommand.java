package com.example.modulith.demo.notifications.management.application.events;

import java.util.UUID;

public record SendNotificationCommand(UUID userId, String text) {}
