package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String discountType; // "PERCENTAGE" or "FLAT"

    private Double discountValue;

    private Double minOrderAmount;

    private String expiryDate;

    private boolean active;
}
