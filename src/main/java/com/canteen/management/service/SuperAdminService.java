package com.canteen.management.service;

import com.canteen.management.dto.SuperAdminLoginRequest;
import com.canteen.management.dto.SuperAdminLoginResponse;

import com.canteen.management.dto.SuperAdminDashboardResponse;

public interface SuperAdminService {

    SuperAdminLoginResponse login(SuperAdminLoginRequest request);

    SuperAdminDashboardResponse getDashboard();

}