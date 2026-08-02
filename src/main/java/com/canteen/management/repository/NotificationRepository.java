package com.canteen.management.repository;

import com.canteen.management.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStudentId(String studentId);

    long countByStudentIdAndReadFalse(String studentId);

}