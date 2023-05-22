package com.example.modulith.demo.notifications.management.application.usecase;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.modulith.demo.notifications.management.api.model.NotificationDTO;
import com.example.modulith.demo.notifications.management.application.dao.NotificationDAO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetUserNotificationsQueryHandler {

    private final NotificationDAO notificationDAO;

    @QueryHandler
    @Transactional(readOnly = true)
    public List<NotificationDTO> execute(GetNotificationsByUserIdQuery query) {
        return notificationDAO.getAllByUserId(query.userId()).stream().map(e -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setText(e.getText());
            notificationDTO.setCreatedAt(e.getCreatedAt());
            return notificationDTO;
        }).toList();
    }
}
