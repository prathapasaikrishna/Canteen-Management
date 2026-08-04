package com.canteen.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodRequest {

    @NotBlank(message = "Food name is required")
    private String foodName;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    private Double price;

    @NotBlank(message = "Available date is required")
    private String availableDate;

    @NotBlank(message = "Available time is required")
    private String availableTime;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotBlank(message = "Status is required")
    private String status;

    private String canteenId;

    private Long organizationId;

    private Long branchId;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    private String imageUrl;


}
