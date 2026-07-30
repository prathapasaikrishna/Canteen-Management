package com.canteen.management.dto;

public class RecommendationResponse {

    private Long foodId;
    private String foodName;
    private String imageUrl;
    private String category;
    private Double price;
    private String recommendationReason;

    public RecommendationResponse() {
    }

    public RecommendationResponse(Long foodId,
                                  String foodName,
                                  String imageUrl,
                                  String category,
                                  Double price,
                                  String recommendationReason) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.recommendationReason = recommendationReason;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getRecommendationReason() {
        return recommendationReason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }
}