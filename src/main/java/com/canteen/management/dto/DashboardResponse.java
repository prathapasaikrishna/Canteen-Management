package com.canteen.management.dto;

public class DashboardResponse {

    private Long totalStudents;
    private Long totalFoods;
    private Long totalOrders;
    private Long totalPayments;
    private Double totalRevenue;

    private String topRatedFood;
    private Double topRating;

    private String mostOrderedFood;
    private Long mostOrderedCount;





    public DashboardResponse() {
    }

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getTotalFoods() {
        return totalFoods;
    }

    public void setTotalFoods(Long totalFoods) {
        this.totalFoods = totalFoods;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getTopRatedFood() {
        return topRatedFood;
    }

    public void setTopRatedFood(String topRatedFood) {
        this.topRatedFood = topRatedFood;
    }

    public Double getTopRating() {
        return topRating;
    }

    public void setTopRating(Double topRating) {
        this.topRating = topRating;
    }

    public String getMostOrderedFood() {
        return mostOrderedFood;
    }

    public void setMostOrderedFood(String mostOrderedFood) {
        this.mostOrderedFood = mostOrderedFood;
    }

    public Long getMostOrderedCount() {
        return mostOrderedCount;
    }

    public void setMostOrderedCount(Long mostOrderedCount) {
        this.mostOrderedCount = mostOrderedCount;
    }

    public Long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Long totalPayments) {
        this.totalPayments = totalPayments;
    }
}