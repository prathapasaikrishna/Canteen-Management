package com.canteen.management.service;

import com.canteen.management.dto.CreateOrderResponse;

public interface RazorpayService {

    CreateOrderResponse createOrder(Double amount) throws Exception;

    boolean verifySignature(
            String orderId,
            String paymentId,
            String signature
    );

}