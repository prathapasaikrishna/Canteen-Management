package com.canteen.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.dto.PaymentRequest;
import com.canteen.management.dto.PaymentResponse;
import com.canteen.management.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentResponse makePayment(@Valid @RequestBody PaymentRequest paymentRequest) {

        return paymentService.makePayment(paymentRequest);
    }

    @GetMapping("/all")
    public List<PaymentResponse> getAllPayments() {

        return paymentService.getAllPayments();
    }

    @GetMapping("/order/{orderNumber}")
    public PaymentResponse getPaymentByOrderNumber(
            @PathVariable String orderNumber) {

        return paymentService.getPaymentByOrderNumber(orderNumber);
    }
}