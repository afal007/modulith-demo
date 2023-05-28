package com.example.modulith.demo.notifications.management.application.usecase;

import com.example.modulith.demo.notifications.management.application.dao.NotificationEntity;
import com.example.modulith.demo.notifications.management.application.dao.NotificationRepository;
import com.example.modulith.demo.notifications.management.application.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SendNotificationUseCase {

    private final NotificationRepository notificationDAO;

    @Transactional
    public void execute(Notification notification) {
        NotificationEntity entity = new NotificationEntity();
        entity.setText(notification.text());
        entity.setUserId(notification.userId());
        notificationDAO.save(entity);
    }
}
