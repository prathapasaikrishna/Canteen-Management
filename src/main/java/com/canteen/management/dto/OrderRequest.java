package com.canteen.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank(message = "Student Id is required")
    private String studentId;

    @NotNull(message = "Food Id is required")
    private Long foodId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    private String paymentMethod;
}