package com.canteen.management.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.canteen.management.service.NotificationService;
import com.canteen.management.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.entity.Notification;
import com.canteen.management.repository.NotificationRepository;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.repository.WalletRepository;
import com.canteen.management.repository.WalletTransactionRepository;
import com.canteen.management.dto.OrderRequest;
import com.canteen.management.dto.OrderResponse;
import com.canteen.management.entity.Food;
import com.canteen.management.entity.Order;
import com.canteen.management.entity.Wallet;
import com.canteen.management.entity.WalletTransaction;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

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
        
        // Dynamic payment method mapping
        String payMethod = orderRequest.getPaymentMethod() != null ? orderRequest.getPaymentMethod().toUpperCase() : "CASH";
        order.setPaymentMethod(payMethod);
        if ("WALLET".equals(payMethod) || "UPI".equals(payMethod)) {
            order.setPaymentStatus("Paid");
        } else {
            order.setPaymentStatus("Pending");
        }
        
        order.setCanteenId(food.getCanteenId());

        Order savedOrder = orderRepository.save(order);

        notificationService.sendPushNotification(
                savedOrder.getStudentId(),
                "🛒 Order Placed",
                "Your order " + savedOrder.getOrderNumber() + " has been placed successfully."
        );

        // Async Email Receipt sending to avoid blocking response
        try {
            new Thread(() -> {
                try {
                    studentRepository.findByStudentId(savedOrder.getStudentId()).ifPresent(student -> {
                        emailService.sendOrderInvoiceEmail(
                                student.getEmail(),
                                student.getName(),
                                savedOrder.getOrderNumber(),
                                food.getFoodName(),
                                savedOrder.getQuantity(),
                                savedOrder.getTotalPrice(),
                                savedOrder.getOrderDate()
                        );
                    });
                } catch (Exception ex) {
                    System.err.println("Failed to send order invoice email: " + ex.getMessage());
                }
            }).start();
        } catch (Exception e) {
            // Safe fallback
        }

        return new OrderResponse(
                savedOrder.getId(),
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
                "Order Placed Successfully",
                savedOrder.getPaymentMethod()
        );
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
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
                    "Success",
                    order.getPaymentMethod() != null ? order.getPaymentMethod() : "UNKNOWN"
            ));
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
                    "Success",
                    order.getPaymentMethod() != null ? order.getPaymentMethod() : "UNKNOWN"
            ));
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

        String notifTitle = "";
        String notifMessage = "";

        if (status.equalsIgnoreCase("ACCEPTED")) {
            notifTitle = "✅ Order Accepted";
            notifMessage = "Your order " + order.getOrderNumber() + " has been accepted.";
        } else if (status.equalsIgnoreCase("PREPARING")) {
            notifTitle = "🍳 Order Preparing";
            notifMessage = "Your order " + order.getOrderNumber() + " is being prepared.";
        } else if (status.equalsIgnoreCase("COMPLETED") || status.equalsIgnoreCase("READY")) {
            notifTitle = "✅ Order Ready";
            notifMessage = "Your order " + order.getOrderNumber() + " is ready for pickup.";
        } else if (status.equalsIgnoreCase("CANCELLED")) {
            notifTitle = "❌ Order Cancelled";
            notifMessage = "Sorry, your order " + order.getOrderNumber() + " has been cancelled.";
            
            // Refund workflow logic
            if ("Paid".equalsIgnoreCase(order.getPaymentStatus()) ||
                    "WALLET".equalsIgnoreCase(order.getPaymentMethod()) ||
                    "UPI".equalsIgnoreCase(order.getPaymentMethod())) {
                
                Wallet wallet = walletRepository.findByStudentId(order.getStudentId())
                        .orElseGet(() -> {
                            Wallet w = new Wallet();
                            w.setStudentId(order.getStudentId());
                            w.setBalance(0.0);
                            return w;
                        });
                
                double refundAmount = order.getTotalPrice();
                wallet.setBalance(wallet.getBalance() + refundAmount);
                walletRepository.save(wallet);

                WalletTransaction transaction = new WalletTransaction();
                transaction.setStudentId(order.getStudentId());
                transaction.setAmount(refundAmount);
                transaction.setType("CREDIT");
                transaction.setDescription("Refund: Order " + order.getOrderNumber());
                transactionRepository.save(transaction);

                order.setPaymentStatus("Refunded");
                notifTitle = "❌ Order Cancelled & Refunded";
                notifMessage = "Your order " + order.getOrderNumber() + " has been cancelled. ₹" + refundAmount + " refunded to dining card.";
            }
        } else if (status.equalsIgnoreCase("COLLECTED")) {
            notifTitle = "🎉 Order Collected";
            notifMessage = "Thank you! Your order " + order.getOrderNumber() + " has been collected.";
            
            try {
                studentRepository.findByStudentId(order.getStudentId()).ifPresent(student -> {
                    int pointsEarned = (int) (order.getTotalPrice() / 100);
                    if (pointsEarned > 0) {
                        int currentPoints = student.getLoyaltyPoints() != null ? student.getLoyaltyPoints() : 0;
                        int newPoints = currentPoints + pointsEarned;
                        student.setLoyaltyPoints(newPoints);
                        
                        if (newPoints > 200) {
                            student.setLoyaltyTier("PLATINUM");
                        } else if (newPoints > 50) {
                            student.setLoyaltyTier("GOLD");
                        } else {
                            student.setLoyaltyTier("SILVER");
                        }
                        studentRepository.save(student);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            notifTitle = "📦 Order Updated";
            notifMessage = "Your order status changed to " + status;
        }

        Order savedOrder = orderRepository.save(order);

        notificationService.sendPushNotification(
                savedOrder.getStudentId(),
                notifTitle,
                notifMessage
        );

        return new OrderResponse(
                savedOrder.getId(),
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
                "Order Status Updated",
                savedOrder.getPaymentMethod() != null ? savedOrder.getPaymentMethod() : "UNKNOWN"
        );
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}