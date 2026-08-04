package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyPaymentResponse {

    private boolean success;

    private String message;

}