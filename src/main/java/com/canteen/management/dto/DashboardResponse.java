package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private Long totalStudents;
    private Long totalFoods;
    private Long totalOrders;
    private Long totalPayments;
    private Double totalRevenue;
}