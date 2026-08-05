package com.canteen.management.service;

import com.canteen.management.dto.*;

import java.util.List;

public interface BranchAdminService {

    BranchAdminResponse addBranchAdmin(BranchAdminRequest request);

    BranchAdminResponse updateBranchAdmin(Long id, BranchAdminRequest request);

    String deleteBranchAdmin(Long id);

    List<BranchAdminResponse> getAllBranchAdmins();

    List<BranchAdminResponse> getBranchAdminsByBranch(Long branchId);

    BranchAdminLoginResponse login(BranchAdminLoginRequest request);

    List<OrderResponse> getTodayOrders(Long branchId);

    List<OrderResponse> getPendingOrders(Long branchId);

    List<OrderResponse> getPreparingOrders(Long branchId);

    List<OrderResponse> getReadyOrders(Long branchId);

    List<OrderResponse> getCollectedOrders(Long branchId);

    List<OrderResponse> getCancelledOrders(Long branchId);

    OrderResponse updateOrderStatus(UpdateOrderStatusRequest request);

}