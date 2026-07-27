package com.canteen.management.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canteen.management.dto.OrderRequest;
import com.canteen.management.dto.OrderResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.entity.Order;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Food food = foodRepository.findById(orderRequest.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food Not Found"));

        if (food.getQuantity() < orderRequest.getQuantity()) {
            throw new RuntimeException("Insufficient Quantity Available");
        }

        Double totalPrice = food.getPrice() * orderRequest.getQuantity();

        food.setQuantity(food.getQuantity() - orderRequest.getQuantity());
        foodRepository.save(food);

        Order order = new Order();
        order.setOrderNumber("ORD" + System.currentTimeMillis());
        order.setStudentId(orderRequest.getStudentId());
        order.setFoodId(orderRequest.getFoodId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDate.now().toString());
        order.setOrderStatus("Ordered");
        order.setPaymentStatus("Pending");
        order.setCanteenId(food.getCanteenId());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getStudentId(),
                savedOrder.getFoodId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getOrderDate(),
                savedOrder.getOrderStatus(),
                savedOrder.getPaymentStatus(),
                savedOrder.getCanteenId(),
                "Order Placed Successfully"
        );
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(
                    new OrderResponse(
                            order.getId(),
                            order.getOrderNumber(),
                            order.getStudentId(),
                            order.getFoodId(),
                            order.getQuantity(),
                            order.getTotalPrice(),
                            order.getOrderDate(),
                            order.getOrderStatus(),
                            order.getPaymentStatus(),
                            order.getCanteenId(),
                            "Success"
                    )
            );
        }

        return responseList;
    }

    @Override
    public List<OrderResponse> getOrdersByStudentId(String studentId) {
        List<Order> orders = orderRepository.findByStudentId(studentId);
        List<OrderResponse> responseList = new ArrayList<>();
        for (Order order : orders) {
            responseList.add(new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStudentId(),
                order.getFoodId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getCanteenId(),
                "Success"
            ));
        }
        return responseList;
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        order.setOrderStatus(status);
        if ("Completed".equalsIgnoreCase(status)) {
            order.setPaymentStatus("Paid");
        }
        Order savedOrder = orderRepository.save(order);
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getStudentId(),
                savedOrder.getFoodId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getOrderDate(),
                savedOrder.getOrderStatus(),
                savedOrder.getPaymentStatus(),
                savedOrder.getCanteenId(),
                "Order Status Updated"
        );
    }
}