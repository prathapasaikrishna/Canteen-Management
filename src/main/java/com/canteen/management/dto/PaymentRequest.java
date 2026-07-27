package com.canteen.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank(message = "Order Number is required")
    private String orderNumber;

    @NotBlank(message = "Student Id is required")
    private String studentId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Payment Method is required")
    private String paymentMethod;
}