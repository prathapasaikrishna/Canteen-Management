package com.canteen.management.dto;

public class ReviewResponse {

    private Long id;
    private String studentId;
    private Long foodId;
    private float rating;
    private String review;
    private String reviewDate;
    private String message;

    public ReviewResponse() {
    }

    public ReviewResponse(Long id, String studentId, Long foodId,
                          float rating, String review,
                          String reviewDate, String message) {

        this.id = id;
        this.studentId = studentId;
        this.foodId = foodId;
        this.rating = rating;
        this.review = review;
        this.reviewDate = reviewDate;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}