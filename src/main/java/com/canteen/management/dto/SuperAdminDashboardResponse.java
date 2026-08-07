package com.canteen.management.dto;

public class SuperAdminDashboardResponse {

    private Long totalOrganizations;
    private Long totalBranches;
    private Long totalStudents;
    private Long totalFoods;
    private Long totalOrders;
    private Double totalRevenue;

    public Long getTotalOrganizations() {
        return totalOrganizations;
    }

    public void setTotalOrganizations(Long totalOrganizations) {
        this.totalOrganizations = totalOrganizations;
    }

    public Long getTotalBranches() {
        return totalBranches;
    }

    public void setTotalBranches(Long totalBranches) {
        this.totalBranches = totalBranches;
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

    private Long dailyRegistrations;
    private Long monthlyRegistrations;
    private Long yearlyRegistrations;

    public Long getDailyRegistrations() {
        return dailyRegistrations;
    }

    public void setDailyRegistrations(Long dailyRegistrations) {
        this.dailyRegistrations = dailyRegistrations;
    }

    public Long getMonthlyRegistrations() {
        return monthlyRegistrations;
    }

    public void setMonthlyRegistrations(Long monthlyRegistrations) {
        this.monthlyRegistrations = monthlyRegistrations;
    }

    public Long getYearlyRegistrations() {
        return yearlyRegistrations;
    }

    public void setYearlyRegistrations(Long yearlyRegistrations) {
        this.yearlyRegistrations = yearlyRegistrations;
    }
}