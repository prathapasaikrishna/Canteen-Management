package com.canteen.management.service;

import com.canteen.management.dto.BranchRequest;
import com.canteen.management.dto.BranchResponse;

import java.util.List;

public interface BranchService {

    BranchResponse addBranch(BranchRequest request);

    List<BranchResponse> getAllBranches();

    List<BranchResponse> getBranchesByOrganization(Long organizationId);

    BranchResponse getBranchById(Long id);

    BranchResponse updateBranch(Long id, BranchRequest request);

    String deleteBranch(Long id);

}