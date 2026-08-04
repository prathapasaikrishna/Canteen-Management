package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "wallet_transactions", indexes = {
    @Index(name = "idx_transactions_student", columnList = "studentId")
})
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String type;      // CREDIT / DEBIT

    private String description;

    private LocalDateTime createdAt;

    @Column(unique = true)
    private String razorpayPaymentId;

    private String razorpayOrderId;

    private String razorpaySignature;

    private String paymentStatus;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}