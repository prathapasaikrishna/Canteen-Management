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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.canteen.management.entity.Student;


import java.util.ArrayList;
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

        // Save Notification in Database immediately so student always gets it in-app!
        try {
            Notification notification = new Notification();
            notification.setStudentId(studentId);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setTime(
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")
                    )
            );
            notification.setRead(false);
            notification.setBroadcast(false);
            notificationRepository.save(notification);
        } catch (Exception e) {
            System.out.println("Error saving notification in DB: " + e.getMessage());
            e.printStackTrace();
        }

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
        List<Notification> notifications = notificationRepository.findByStudentId(studentId);
        List<NotificationResponse> responseList = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationResponse res = new NotificationResponse();
            res.setId(notification.getId());
            res.setTitle(notification.getTitle());
            res.setMessage(notification.getMessage());
            res.setTime(notification.getTime());
            res.setRead(notification.isRead());
            responseList.add(res);
        }
        return responseList;
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
    @Override
    public void sendBroadcastNotification(String title, String message) {

        List<Student> students = studentRepository.findAll();

        for (Student student : students) {

            // Save Notification in Database
            Notification notification = new Notification();

            notification.setStudentId(student.getStudentId());
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setTime(
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")
                    )
            );
            notification.setRead(false);
            notification.setBroadcast(true);

            notificationRepository.save(notification);

            // Skip if no FCM Token
            if (student.getFcmToken() == null || student.getFcmToken().isBlank()) {
                continue;
            }

            try {

                Message firebaseMessage = Message.builder()
                        .setToken(student.getFcmToken())
                        .setNotification(
                                com.google.firebase.messaging.Notification.builder()
                                        .setTitle(title)
                                        .setBody(message)
                                        .build()
                        )
                        .build();

                String response = FirebaseMessaging
                        .getInstance()
                        .send(firebaseMessage);

                System.out.println(
                        "Broadcast Sent to "
                                + student.getStudentId()
                                + " : "
                                + response
                );

            } catch (Exception e) {

                System.out.println(
                        "Failed for Student : "
                                + student.getStudentId()
                );

                e.printStackTrace();
            }
        }
    }

    @Override
    public long getUnreadCount(String studentId) {

        return notificationRepository
                .countByStudentIdAndReadFalse(studentId);

    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}