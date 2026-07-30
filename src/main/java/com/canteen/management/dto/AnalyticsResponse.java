package com.canteen.management.dto;

public class AnalyticsResponse {

    private Double todayRevenue;
    private Double weeklyRevenue;
    private Double monthlyRevenue;

    private Integer todayOrders;
    private Integer weeklyOrders;
    private Integer monthlyOrders;

    private Integer lowStockFoods;
    private Integer soldOutFoods;

    public Double getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(Double todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public Double getWeeklyRevenue() {
        return weeklyRevenue;
    }

    public void setWeeklyRevenue(Double weeklyRevenue) {
        this.weeklyRevenue = weeklyRevenue;
    }

    public Double getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(Double monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public Integer getTodayOrders() {
        return todayOrders;
    }

    public void setTodayOrders(Integer todayOrders) {
        this.todayOrders = todayOrders;
    }

    public Integer getWeeklyOrders() {
        return weeklyOrders;
    }

    public void setWeeklyOrders(Integer weeklyOrders) {
        this.weeklyOrders = weeklyOrders;
    }

    public Integer getMonthlyOrders() {
        return monthlyOrders;
    }

    public void setMonthlyOrders(Integer monthlyOrders) {
        this.monthlyOrders = monthlyOrders;
    }

    public Integer getLowStockFoods() {
        return lowStockFoods;
    }

    public void setLowStockFoods(Integer lowStockFoods) {
        this.lowStockFoods = lowStockFoods;
    }

    public Integer getSoldOutFoods() {
        return soldOutFoods;
    }

    public void setSoldOutFoods(Integer soldOutFoods) {
        this.soldOutFoods = soldOutFoods;
    }
}