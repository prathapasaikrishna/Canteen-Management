package com.canteen.management.dto;

public class CreateOrderResponse {

    private String orderId;
    private String key;
    private Double amount;
    private String currency;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(String orderId,
                               String key,
                               Double amount,
                               String currency) {
        this.orderId = orderId;
        this.key = key;
        this.amount = amount;
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}