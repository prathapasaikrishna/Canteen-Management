package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDashboardResponse {

    private Long totalOrders;
    private Long totalPayments;
    private Double totalSpent;
}