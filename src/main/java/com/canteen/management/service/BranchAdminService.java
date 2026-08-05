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

}