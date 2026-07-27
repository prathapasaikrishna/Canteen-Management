package com.canteen.management.service;

import com.canteen.management.dto.DashboardResponse;

public interface AdminService {

    DashboardResponse getDashboard(String canteenId);
}