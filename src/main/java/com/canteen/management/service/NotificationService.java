package com.canteen.management.service;

import com.canteen.management.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications(String studentId);

}