package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RazorpayOrderResponse {

    private String orderId;
    private String key;
    private Double amount;
    private String currency;

}