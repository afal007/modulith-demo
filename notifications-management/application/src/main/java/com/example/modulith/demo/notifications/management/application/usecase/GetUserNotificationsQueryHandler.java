package com.example.modulith.demo.notifications.management.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.modulith.demo.notifications.management.application.dao.NotificationDAO;
import com.example.modulith.demo.notifications.management.application.domain.Notification;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetUserNotificationsQueryHandler {

    private final NotificationDAO notificationDAO;

    @Transactional(readOnly = true)
    public List<Notification> execute(UUID userId) {
        return notificationDAO.getAllByUserId(userId)
            .stream()
            .map(e -> new Notification(e.getUserId(), e.getText(), e.getCreatedAt()))
            .toList();
    }
}
