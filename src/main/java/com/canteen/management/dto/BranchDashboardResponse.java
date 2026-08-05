package com.canteen.management.dto;

import lombok.Data;

@Data
public class BranchDashboardResponse {

    private Long totalFoods;

    private Long availableFoods;

    private Long totalStudents;

    private Long todayOrders;

    private Long pendingOrders;

    private Long completedOrders;

    private Double todayRevenue;

}