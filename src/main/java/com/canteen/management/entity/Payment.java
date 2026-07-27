package com.canteen.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentId;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private String paymentDate;

    public Payment() {
    }

    public Payment(Long id, String paymentId, String orderNumber,
                   String studentId, Double amount,
                   String paymentMethod, String paymentStatus,
                   String paymentDate) {
        this.id = id;
        this.paymentId = paymentId;
        this.orderNumber = orderNumber;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public Long getId() {
        return id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}