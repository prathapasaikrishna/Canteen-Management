package com.canteen.management.dto;

import lombok.Data;

@Data
public class RazorpayOrderRequest {

    private String studentId;
    private Double amount;

}