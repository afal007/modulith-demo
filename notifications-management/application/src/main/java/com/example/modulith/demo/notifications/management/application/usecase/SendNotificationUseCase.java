package com.example.modulith.demo.notifications.management.application.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.modulith.demo.notifications.management.application.dao.NotificationDAO;
import com.example.modulith.demo.notifications.management.application.dao.NotificationEntity;
import com.example.modulith.demo.notifications.management.application.domain.Notification;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendNotificationUseCase {

    private final NotificationDAO notificationDAO;

    @Transactional
    public void execute(Notification notification) {
        NotificationEntity entity = new NotificationEntity();
        entity.setText(notification.text());
        entity.setUserId(notification.userId());
        notificationDAO.save(entity);
    }
}
