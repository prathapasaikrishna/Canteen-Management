package com.canteen.management.dto;

import lombok.Data;

@Data
public class AdminDashboardResponse {

    private Long totalOrders;

    private Long pendingOrders;

    private Long completedOrders;

    private Long totalFoods;

    private Double totalRevenue;

    private Long totalStudents;

}