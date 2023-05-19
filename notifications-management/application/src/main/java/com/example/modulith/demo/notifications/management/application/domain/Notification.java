package com.example.modulith.demo.notifications.management.application.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Notification(UUID userId, String text, OffsetDateTime createdAt) {}
