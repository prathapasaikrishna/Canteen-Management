package com.canteen.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canteen.management.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    java.util.List<Order> findByStudentId(String studentId);

    java.util.List<Order> findByCanteenId(String canteenId);

    Long countByCanteenId(String canteenId);
}