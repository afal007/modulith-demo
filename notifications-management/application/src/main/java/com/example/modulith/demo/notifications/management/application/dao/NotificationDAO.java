package com.example.modulith.demo.notifications.management.application.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDAO extends JpaRepository<NotificationEntity, UUID> {

    List<NotificationEntity> getAllByUserId(UUID userId);
}
