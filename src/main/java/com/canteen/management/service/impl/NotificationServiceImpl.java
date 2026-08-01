package com.canteen.management.service.impl;

import com.canteen.management.dto.NotificationResponse;
import com.canteen.management.entity.Notification;
import com.canteen.management.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.service.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendPushNotification(
            String studentId,
            String title,
            String message
    ) {

        // Firebase code later

    }

    @Override
    public List<NotificationResponse> getNotifications(String studentId) {
        return List.of();
    }

    @Override
    public void markAllRead(String studentId) {

        List<Notification> notifications =
                notificationRepository.findByStudentId(studentId);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);
    }
}