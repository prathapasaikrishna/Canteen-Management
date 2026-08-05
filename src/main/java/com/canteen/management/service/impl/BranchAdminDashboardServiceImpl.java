package com.canteen.management.service.impl;

import com.canteen.management.dto.BranchDashboardResponse;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.service.BranchAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchAdminDashboardServiceImpl
        implements BranchAdminDashboardService {

    private final FoodRepository foodRepository;

    private final StudentRepository studentRepository;

    private final OrderRepository orderRepository;

    @Override
    public BranchDashboardResponse getDashboard(Long branchId) {

        BranchDashboardResponse response =
                new BranchDashboardResponse();

        response.setTotalFoods(
                foodRepository.countByBranchId(branchId));

        response.setAvailableFoods(
                foodRepository.countByBranchIdAndStatus(
                        branchId,
                        "AVAILABLE"
                ));

        response.setTotalStudents(
                studentRepository.countByBranchId(branchId));

        response.setTodayOrders(
                orderRepository.countByBranchId(branchId));

        response.setPendingOrders(
                orderRepository.countByBranchIdAndStatus(
                        branchId,
                        "PENDING"
                ));

        response.setCompletedOrders(
                orderRepository.countByBranchIdAndStatus(
                        branchId,
                        "COMPLETED"
                ));

        Double revenue =
                orderRepository.getTotalRevenue(branchId);

        response.setTodayRevenue(
                revenue == null ? 0.0 : revenue);

        return response;
    }
}