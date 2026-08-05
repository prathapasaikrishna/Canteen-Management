package com.canteen.management.service;

import java.util.List;

import com.canteen.management.dto.OrderRequest;
import com.canteen.management.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByStudentId(String studentId);

    OrderResponse updateOrderStatus(Long id, String status);

    void deleteOrder(Long id);
}