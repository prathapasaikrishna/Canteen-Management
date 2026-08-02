package com.canteen.management.service.impl;

import com.canteen.management.dto.NotificationResponse;
import com.canteen.management.entity.Notification;
import com.canteen.management.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.service.NotificationService;
import com.canteen.management.entity.Student;
import com.canteen.management.repository.StudentRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;


import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private StudentRepository studentRepository;



    @Override
    public void sendPushNotification(
            String studentId,
            String title,
            String message) {

        Student student = studentRepository
                .findByStudentId(studentId)
                .orElse(null);

        if (student == null) {
            return;
        }

        if (student.getFcmToken() == null ||
                student.getFcmToken().isEmpty()) {
            return;
        }

        try {

            Message firebaseMessage =
                    Message.builder()
                            .setToken(student.getFcmToken())
                            .setNotification(
                                    com.google.firebase.messaging.Notification.builder()
                                            .setTitle(title)
                                            .setBody(message)
                                            .build()
                            )
                            .build();

            String response =
                    FirebaseMessaging.getInstance()
                            .send(firebaseMessage);

            System.out.println("Notification Sent : " + response);

        } catch (Exception e) {

            e.printStackTrace();

        }
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

    @Override
    public void notifyAllStudents(String title, String message) {

        List<Student> students = studentRepository.findAll();

        for (Student student : students) {

            if (student.getFcmToken() == null ||
                    student.getFcmToken().isBlank()) {
                continue;
            }

            try {

                Message firebaseMessage =
                        Message.builder()
                                .setToken(student.getFcmToken())
                                .setNotification(
                                        com.google.firebase.messaging.Notification.builder()
                                                .setTitle(title)
                                                .setBody(message)
                                                .build()
                                )
                                .build();

                FirebaseMessaging.getInstance().send(firebaseMessage);

                System.out.println("Notification Sent To : "
                        + student.getStudentId());

            } catch (Exception e) {

                System.out.println("Failed : "
                        + student.getStudentId());

                e.printStackTrace();
            }
        }
    }
}