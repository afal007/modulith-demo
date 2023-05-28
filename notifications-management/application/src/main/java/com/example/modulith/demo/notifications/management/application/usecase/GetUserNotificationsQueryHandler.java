package com.example.modulith.demo.notifications.management.application.usecase;

import com.example.modulith.demo.notifications.management.api.model.NotificationDTO;
import com.example.modulith.demo.notifications.management.application.dao.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetUserNotificationsQueryHandler {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationDTO> execute(GetNotificationsByUserIdQuery query) {
        return notificationRepository.getAllByUserId(query.userId()).stream().map(e -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setText(e.getText());
            notificationDTO.setCreatedAt(e.getCreatedAt());
            return notificationDTO;
        }).toList();
    }
}
