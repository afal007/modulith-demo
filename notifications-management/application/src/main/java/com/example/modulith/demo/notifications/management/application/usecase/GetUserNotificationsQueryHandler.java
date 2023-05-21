package com.example.modulith.demo.notifications.management.application.usecase;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.modulith.demo.notifications.management.application.dao.NotificationDAO;
import com.example.modulith.demo.notifications.management.application.domain.Notification;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetUserNotificationsQueryHandler {

    private final NotificationDAO notificationDAO;

    @QueryHandler
    @Transactional(readOnly = true)
    public List<Notification> execute(GetNotificationsByUserIdQuery query) {
        return notificationDAO.getAllByUserId(query.userId())
            .stream()
            .map(e -> new Notification(e.getUserId(), e.getText(), e.getCreatedAt()))
            .toList();
    }
}
