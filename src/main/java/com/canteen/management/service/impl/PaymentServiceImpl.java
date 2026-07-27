package com.canteen.management.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canteen.management.dto.PaymentRequest;
import com.canteen.management.dto.PaymentResponse;
import com.canteen.management.entity.Order;
import com.canteen.management.entity.Payment;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.repository.PaymentRepository;
import com.canteen.management.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {

        Order order = (Order) orderRepository.findByOrderNumber(paymentRequest.getOrderNumber())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        Payment payment = new Payment();

        payment.setPaymentId("PAY" + System.currentTimeMillis());
        payment.setOrderNumber(paymentRequest.getOrderNumber());
        payment.setStudentId(paymentRequest.getStudentId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDate.now().toString());

        Payment savedPayment = paymentRepository.save(payment);

        order.setPaymentStatus("Paid");
        orderRepository.save(order);

        return new PaymentResponse(
                savedPayment.getId(),
                savedPayment.getPaymentId(),
                savedPayment.getOrderNumber(),
                savedPayment.getStudentId(),
                savedPayment.getAmount(),
                savedPayment.getPaymentMethod(),
                savedPayment.getPaymentStatus(),
                savedPayment.getPaymentDate(),
                "Payment Successful"
        );
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponse> responseList = new ArrayList<>();

        for (Payment payment : payments) {

            responseList.add(
                    new PaymentResponse(
                            payment.getId(),
                            payment.getPaymentId(),
                            payment.getOrderNumber(),
                            payment.getStudentId(),
                            payment.getAmount(),
                            payment.getPaymentMethod(),
                            payment.getPaymentStatus(),
                            payment.getPaymentDate(),
                            "Success"
                    )
            );
        }

        return responseList;
    }

    @Override
    public PaymentResponse getPaymentByOrderNumber(String orderNumber) {

        Payment payment = paymentRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Payment Not Found"));

        return new PaymentResponse(
                payment.getId(),
                payment.getPaymentId(),
                payment.getOrderNumber(),
                payment.getStudentId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentDate(),
                "Success"
        );
    }
}