package com.canteen.management.service;

import com.canteen.management.dto.StudentDashboardResponse;

public interface StudentDashboardService {

    StudentDashboardResponse getDashboard(String studentId);
}