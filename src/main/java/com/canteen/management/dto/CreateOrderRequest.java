package com.canteen.management.dto;

public class CreateOrderRequest {

    private String studentId;
    private Double amount;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(String studentId, Double amount) {
        this.studentId = studentId;
        this.amount = amount;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}