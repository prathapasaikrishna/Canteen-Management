package com.canteen.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canteen.management.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderNumber(String orderNumber);

    Optional<Payment> findByPaymentId(String paymentId);

}