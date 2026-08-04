package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private String studentId;
    private Long foodId;
    private Integer quantity;
    private Double totalPrice;
    private String orderDate;
    private String orderStatus;
    private String qrCode;
    private String paymentStatus;
    private String canteenId;
    private String message;
    private String paymentMethod;

    public OrderResponse(Long id, String orderNumber, String studentId, Long foodId,
                         Integer quantity, Double totalPrice, String orderDate,
                         String orderStatus, String qrCode, String paymentStatus,
                         String canteenId, String message) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.studentId = studentId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.qrCode = qrCode;
        this.paymentStatus = paymentStatus;
        this.canteenId = canteenId;
        this.message = message;
        this.paymentMethod = "UNKNOWN";
    }

    public OrderResponse(Long id, String orderNumber, String studentId, Long foodId,
                         Integer quantity, Double totalPrice, String orderDate,
                         String orderStatus, String qrCode, String paymentStatus,
                         String canteenId, String message, String paymentMethod) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.studentId = studentId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.qrCode = qrCode;
        this.paymentStatus = paymentStatus;
        this.canteenId = canteenId;
        this.message = message;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
