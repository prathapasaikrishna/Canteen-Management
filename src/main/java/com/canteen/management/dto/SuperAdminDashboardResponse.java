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
}