package com.canteen.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canteen.management.dto.DashboardResponse;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.repository.PaymentRepository;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public DashboardResponse getDashboard(String canteenId) {
        if (canteenId != null && !canteenId.trim().isEmpty()) {
            Long totalStudents = studentRepository.count();
            Long totalFoods = foodRepository.countByCanteenId(canteenId);
            Long totalOrders = orderRepository.countByCanteenId(canteenId);
            java.util.List<com.canteen.management.entity.Order> canteenOrders = orderRepository.findByCanteenId(canteenId);
            Long totalPayments = (long) canteenOrders.size();
            Double totalRevenue = canteenOrders.stream()
                    .mapToDouble(order -> order.getTotalPrice())
                    .sum();

            return new DashboardResponse(
                    totalStudents,
                    totalFoods,
                    totalOrders,
                    totalPayments,
                    totalRevenue
            );
        } else {
            Long totalStudents = studentRepository.count();
            Long totalFoods = foodRepository.count();
            Long totalOrders = orderRepository.count();
            Long totalPayments = paymentRepository.count();
            Double totalRevenue = paymentRepository.findAll()
                    .stream()
                    .mapToDouble(payment -> payment.getAmount())
                    .sum();

            return new DashboardResponse(
                    totalStudents,
                    totalFoods,
                    totalOrders,
                    totalPayments,
                    totalRevenue
            );
        }
    }
}