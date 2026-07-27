package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long id;
    private String paymentId;
    private String orderNumber;
    private String studentId;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;
    private String message;
}