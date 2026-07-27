package com.canteen.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canteen.management.dto.StudentDashboardResponse;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.repository.PaymentRepository;
import com.canteen.management.service.StudentDashboardService;

@Service
public class StudentDashboardServiceImpl implements StudentDashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public StudentDashboardResponse getDashboard(String studentId) {

        Long totalOrders = orderRepository.findAll()
                .stream()
                .filter(order -> order.getStudentId().equals(studentId))
                .count();

        Long totalPayments = paymentRepository.findAll()
                .stream()
                .filter(payment -> payment.getStudentId().equals(studentId))
                .count();

        Double totalSpent = paymentRepository.findAll()
                .stream()
                .filter(payment -> payment.getStudentId().equals(studentId))
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        return new StudentDashboardResponse(
                totalOrders,
                totalPayments,
                totalSpent
        );
    }
}