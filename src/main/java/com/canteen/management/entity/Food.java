package com.canteen.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String foodName;


    // Category relation
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


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


    @Column(name = "organization_id")
    private Long organizationId;


    @Column(name = "branch_id")
    private Long branchId;


    @Column(name = "image_url")
    private String imageUrl;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



    public Food() {
    }



    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }


    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
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



    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
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



    public Long getOrganizationId() {
        return organizationId;
    }


    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }



    public Long getBranchId() {
        return branchId;
    }


    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }



    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}