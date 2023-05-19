package com.example.modulith.demo.notifications.management.application.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingsDAO extends JpaRepository<NotificationEntity, UUID> {}
