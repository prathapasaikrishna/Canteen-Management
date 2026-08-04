package com.canteen.management.dto;

import lombok.Data;

@Data
public class VerifyPaymentRequest {

    private String studentId;

    private Double amount;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

}