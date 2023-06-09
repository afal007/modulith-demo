package com.example.modulith.demo.notifications.management.application.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import com.example.modulith.demo.notifications.management.api.model.NotificationDTO;
import com.example.modulith.demo.notifications.management.api.model.NotificationSettingsDTO;
import com.example.modulith.demo.notifications.management.api.spring.web.NotificationV1Api;
import com.example.modulith.demo.notifications.management.application.usecase.GetNotificationsByUserIdQuery;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NotificationsManagementController implements NotificationV1Api {

    private final QueryGateway queryGateway;

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersCurrentNotificationSettings() {
        return NotificationV1Api.super.getV1UsersCurrentNotificationSettings();
    }

    @Override
    public ResponseEntity<List<NotificationDTO>> getV1UsersCurrentNotifications() {
        return ResponseEntity.ok(queryGateway.query(new GetNotificationsByUserIdQuery(getCurrentUserId().orElseThrow()),
            ResponseTypes.multipleInstancesOf(NotificationDTO.class)
        ).join());
    }

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersIdNotificationSettings(Long id) {
        return NotificationV1Api.super.getV1UsersIdNotificationSettings(id);
    }

    @Override
    public ResponseEntity<List<NotificationDTO>> getV1UsersIdNotifications(UUID id) {
        return ResponseEntity.ok(queryGateway.query(new GetNotificationsByUserIdQuery(id),
            ResponseTypes.multipleInstancesOf(NotificationDTO.class)
        ).join());
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
