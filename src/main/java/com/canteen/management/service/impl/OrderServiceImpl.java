package com.canteen.management.service.impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.canteen.management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.entity.Notification;
import com.canteen.management.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.canteen.management.dto.OrderRequest;
import com.canteen.management.dto.OrderResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.entity.Order;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.service.OrderService;




@Service
public class OrderServiceImpl implements OrderService {


    @Autowired    private OrderRepository orderRepository;

    @Autowired    private FoodRepository foodRepository;

    @Autowired    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;



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
        order.setQrCode("QR" + System.currentTimeMillis());
        order.setStudentId(orderRequest.getStudentId());
        order.setFoodId(orderRequest.getFoodId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDate.now().toString());
        order.setOrderStatus("PLACED");
        order.setPaymentStatus("Pending");
        order.setCanteenId(food.getCanteenId());


        Order savedOrder = orderRepository.save(order);

        notificationService.sendPushNotification(
                savedOrder.getStudentId(),
                "🛒 Order Placed",
                "Your order " + savedOrder.getOrderNumber() + " has been placed successfully."
        );

        return new OrderResponse(savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getStudentId(),
                savedOrder.getFoodId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getOrderDate(),
                savedOrder.getOrderStatus(),
                savedOrder.getQrCode(),
                savedOrder.getPaymentStatus(),
                savedOrder.getCanteenId(),
                "Order Placed Successfully");
    }

    @Override

         public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responseList = new ArrayList<>();
        for (Order order : orders) {

            responseList.add( new OrderResponse( order.getId(),

                    order.getOrderNumber(),
                    order.getStudentId(),
                    order.getFoodId(),
                    order.getQuantity(),
                    order.getTotalPrice(),
                    order.getOrderDate(),
                    order.getOrderStatus(),
                    order.getQrCode(),
                    order.getPaymentStatus(),
                    order.getCanteenId(),
                    "Success"  ) );

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
                    order.getQrCode(),
                    order.getPaymentStatus(),
                    order.getCanteenId(),
                    "Success" ));

        }
                   return responseList;
    }


            @Override
           public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        order.setOrderStatus(status);

        if ("COLLECTED".equalsIgnoreCase(status) || "Collected".equalsIgnoreCase(status)) {
            order.setPaymentStatus("Paid");
            order.setQrCode(null);
        }
        Order savedOrder = orderRepository.save(order);
        String notifTitle = "";
        String notifMessage = "";
        if (status.equalsIgnoreCase("ACCEPTED")) {
            notifTitle = "✅ Order Accepted";
            notifMessage = "Your order " + savedOrder.getOrderNumber() + " has been accepted.";
        } else if (status.equalsIgnoreCase("PREPARING")) {
            notifTitle = "🍳 Order Preparing";
            notifMessage = "Your order " + savedOrder.getOrderNumber() + " is being prepared.";
        } else if (status.equalsIgnoreCase("COMPLETED") || status.equalsIgnoreCase("READY")) {
            notifTitle = "✅ Order Ready";
            notifMessage = "Your order " + savedOrder.getOrderNumber() + " is ready for pickup.";
        } else if (status.equalsIgnoreCase("CANCELLED")) {
            notifTitle = "❌ Order Cancelled";
            notifMessage = "Sorry, your order " + savedOrder.getOrderNumber() + " has been cancelled.";
        } else if (status.equalsIgnoreCase("COLLECTED")) {
            notifTitle = "🎉 Order Collected";
            notifMessage = "Thank you! Your order " + savedOrder.getOrderNumber() + " has been collected.";
        } else {
            notifTitle = "📦 Order Updated";
            notifMessage = "Your order status changed to " + status;
        }

        notificationService.sendPushNotification(
                savedOrder.getStudentId(),
                notifTitle,
                notifMessage
        );


                return new OrderResponse(savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getStudentId(),
                savedOrder.getFoodId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getOrderDate(),
                savedOrder.getOrderStatus(),
                savedOrder.getQrCode(),
                savedOrder.getPaymentStatus(),
                savedOrder.getCanteenId(),
                "Order Status Updated" );
    }
}