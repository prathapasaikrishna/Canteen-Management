package com.canteen.management.service;

import com.canteen.management.dto.BranchDashboardResponse;

public interface BranchAdminDashboardService {

    BranchDashboardResponse getDashboard(Long branchId);

}