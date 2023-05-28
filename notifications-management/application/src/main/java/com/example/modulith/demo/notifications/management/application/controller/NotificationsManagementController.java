package com.example.modulith.demo.notifications.management.application.controller;

import com.example.modulith.demo.notifications.management.api.model.NotificationDTO;
import com.example.modulith.demo.notifications.management.api.model.NotificationSettingsDTO;
import com.example.modulith.demo.notifications.management.api.spring.web.NotificationV1Api;
import com.example.modulith.demo.notifications.management.application.usecase.GetNotificationsByUserIdQuery;
import com.example.modulith.demo.notifications.management.application.usecase.GetUserNotificationsQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NotificationsManagementController implements NotificationV1Api {

    private final GetUserNotificationsQueryHandler queryHandler;

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersCurrentNotificationSettings() {
        return NotificationV1Api.super.getV1UsersCurrentNotificationSettings();
    }

    @Override
    public ResponseEntity<List<NotificationDTO>> getV1UsersCurrentNotifications() {
        var result = queryHandler.execute(new GetNotificationsByUserIdQuery(getCurrentUserId().orElseThrow()));
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersIdNotificationSettings(Long id) {
        return NotificationV1Api.super.getV1UsersIdNotificationSettings(id);
    }

    @Override
    public ResponseEntity<List<NotificationDTO>> getV1UsersIdNotifications(UUID id) {
        var result = queryHandler.execute(new GetNotificationsByUserIdQuery(id));
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> putV1UsersCurrentNotificationSettings(NotificationSettingsDTO notificationSettingsDTO) {
        return NotificationV1Api.super.putV1UsersCurrentNotificationSettings(notificationSettingsDTO);
    }

    @Override
    public ResponseEntity<Void> putV1UsersIdNotificationSettings(
        UUID id, NotificationSettingsDTO notificationSettingsDTO
    ) {
        return NotificationV1Api.super.putV1UsersIdNotificationSettings(id, notificationSettingsDTO);
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
