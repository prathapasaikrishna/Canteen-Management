package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private String studentId;
    private Long foodId;
    private Integer quantity;
    private Double totalPrice;
    private String orderDate;
    private String orderStatus;
    private String paymentStatus;
    private String canteenId;
    private String message;
}