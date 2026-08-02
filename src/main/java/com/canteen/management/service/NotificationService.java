package com.canteen.management.service;

import com.canteen.management.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    void sendPushNotification(
            String studentId,
            String title,
            String message
    );

    List<NotificationResponse> getNotifications(String studentId);

    void markAllRead(String studentId);

    void notifyAllStudents(
            String title,
            String body
    );

}