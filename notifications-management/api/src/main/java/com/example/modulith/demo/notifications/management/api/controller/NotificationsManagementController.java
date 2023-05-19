package com.example.modulith.demo.notifications.management.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import com.example.modulith.demo.notifications.management.api.model.NotificationDTO;
import com.example.modulith.demo.notifications.management.api.model.NotificationSettingsDTO;
import com.example.modulith.demo.notifications.management.api.spring.web.NotificationSettingsV1Api;
import com.example.modulith.demo.notifications.management.application.usecase.GetUserNotificationsQueryHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NotificationsManagementController implements NotificationSettingsV1Api {

    private final GetUserNotificationsQueryHandler queryHandler;

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersCurrentNotificationSettings() {
        return NotificationSettingsV1Api.super.getV1UsersCurrentNotificationSettings();
    }

    @Override
    public ResponseEntity<List<NotificationDTO>> getV1UsersCurrentNotifications() {
        return ResponseEntity.ok(queryHandler.execute(getCurrentUserId().orElseThrow()).stream().map(n -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setText(n.text());
            notificationDTO.setCreatedAt(n.createdAt());
            return notificationDTO;
        }).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersIdNotificationSettings(Long id) {
        return NotificationSettingsV1Api.super.getV1UsersIdNotificationSettings(id);
    }

    @Override
    public ResponseEntity<List<NotificationDTO>> getV1UsersIdNotifications(UUID id) {
        return ResponseEntity.ok(queryHandler.execute(id).stream().map(n -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setText(n.text());
            notificationDTO.setCreatedAt(n.createdAt());
            return notificationDTO;
        }).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> putV1UsersCurrentNotificationSettings(NotificationSettingsDTO notificationSettingsDTO) {
        return NotificationSettingsV1Api.super.putV1UsersCurrentNotificationSettings(notificationSettingsDTO);
    }

    @Override
    public ResponseEntity<Void> putV1UsersIdNotificationSettings(
        UUID id, NotificationSettingsDTO notificationSettingsDTO
    ) {
        return NotificationSettingsV1Api.super.putV1UsersIdNotificationSettings(id, notificationSettingsDTO);
    }

    private Optional<UUID> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken)) {
            return Optional.empty();
        }

        return Optional.of((JwtAuthenticationToken) authentication)
            .map(JwtAuthenticationToken::getToken)
            .map(JwtClaimAccessor::getSubject)
            .map(UUID::fromString);
    }
}
