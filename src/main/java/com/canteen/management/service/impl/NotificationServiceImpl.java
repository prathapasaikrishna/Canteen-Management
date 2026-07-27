package com.canteen.management.service.impl;

import com.canteen.management.dto.NotificationResponse;
import com.canteen.management.entity.Notification;
import com.canteen.management.repository.NotificationRepository;
import com.canteen.management.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponse> getNotifications(String studentId) {

        List<Notification> notifications =
                notificationRepository.findByStudentIdOrderByIdDesc(studentId);

        List<NotificationResponse> responseList = new ArrayList<>();

        for (Notification notification : notifications) {

            NotificationResponse response = new NotificationResponse();

            response.setId(notification.getId());
            response.setTitle(notification.getTitle());
            response.setMessage(notification.getMessage());
            response.setTime(notification.getTime());
            response.setRead(notification.isRead());

            responseList.add(response);
        }

        return responseList;
    }
}