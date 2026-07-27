package com.canteen.management.service;

import java.util.List;

import com.canteen.management.dto.PaymentRequest;
import com.canteen.management.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest paymentRequest);

    List<PaymentResponse> getAllPayments();

    PaymentResponse getPaymentByOrderNumber(String orderNumber);

}
