package com.canteen.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String availableDate;

    @Column(nullable = false)
    private String availableTime;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String status;

    @Column(name = "canteen_id")
    private String canteenId;

    public Food() {
    }

    public Food(Long id, String foodName, String category, Double price,
                String availableDate, String availableTime,
                Integer quantity, String status) {
        this.id = id;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
        this.availableDate = availableDate;
        this.availableTime = availableTime;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }
}