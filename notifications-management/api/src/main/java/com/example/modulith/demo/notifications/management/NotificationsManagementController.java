package com.example.modulith.demo.notifications.management;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.modulith.demo.notifications.management.api.model.NotificationSettingsDTO;
import com.example.modulith.demo.notifications.management.api.spring.web.NotificationSettingsV1Api;

@RestController
public class NotificationsManagementController implements NotificationSettingsV1Api {

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersCurrentNotificationSettings() {
        return NotificationSettingsV1Api.super.getV1UsersCurrentNotificationSettings();
    }

    @Override
    public ResponseEntity<NotificationSettingsDTO> getV1UsersIdNotificationSettings(Long id) {
        return NotificationSettingsV1Api.super.getV1UsersIdNotificationSettings(id);
    }

    @Override
    public ResponseEntity<Void> putV1UsersCurrentNotificationSettings(NotificationSettingsDTO notificationSettingsDTO) {
        return NotificationSettingsV1Api.super.putV1UsersCurrentNotificationSettings(notificationSettingsDTO);
    }

    @Override
    public ResponseEntity<Void> putV1UsersIdNotificationSettings(
        Long id, NotificationSettingsDTO notificationSettingsDTO
    ) {
        return NotificationSettingsV1Api.super.putV1UsersIdNotificationSettings(id, notificationSettingsDTO);
    }
}
