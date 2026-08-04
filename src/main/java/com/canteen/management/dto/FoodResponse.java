package com.canteen.management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodResponse {

    private Long id;
    private String foodName;
    private String category;
    private Double price;
    private String imageUrl;
    private String availableDate;
    private String availableTime;
    private Integer quantity;
    private String status;
    private String canteenId;
    private Long organizationId;

    private Long branchId;
    private String message;
    private Double averageRating = 0.0;
    private Long totalReviews = 0L;

    public FoodResponse(
            Long id,
            String foodName,
            String category,
            Double price,
            String imageUrl,
            String availableDate,
            String availableTime,
            Integer quantity,
            String status,
            String canteenId,
            String message,
            Double averageRating,
            Long totalReviews,
            Long organizationId,
            Long branchId
    ) {
        this.id = id;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.availableDate = availableDate;
        this.availableTime = availableTime;
        this.quantity = quantity;
        this.status = status;
        this.canteenId = canteenId;
        this.message = message;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;

        this.organizationId = organizationId;
        this.branchId = branchId;
    }


}