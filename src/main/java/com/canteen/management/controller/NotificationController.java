package com.canteen.management.controller;

import com.canteen.management.dto.BroadcastNotificationRequest;
import com.canteen.management.dto.NotificationResponse;
import com.canteen.management.service.NotificationService;
import com.canteen.management.dto.NotificationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{studentId}")
    public List<NotificationResponse> getNotifications(
            @PathVariable String studentId) {

        return notificationService.getNotifications(studentId);
    }

    @PutMapping("/read/{studentId}")
    public ResponseEntity<Void> markRead(@PathVariable String studentId){

        notificationService.markAllRead(studentId);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    public String testNotification() {

        notificationService.sendPushNotification(
                "STU002",
                "Smart Foods",
                "Hello Darling ❤️"
        );

        return "Notification Sent Successfully";
    }

    @PostMapping("/broadcast")
    public String broadcast(
            @RequestBody BroadcastNotificationRequest request){

        notificationService.sendBroadcastNotification(
                request.getTitle(),
                request.getMessage());

        return "Broadcast Sent Successfully";

    }

    @GetMapping("/unread/{studentId}")
    public long unreadCount(
            @PathVariable String studentId){

        return notificationService
                .getUnreadCount(studentId);

    }
}