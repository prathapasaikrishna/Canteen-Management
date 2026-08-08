package com.canteen.management.service.impl;

import com.canteen.management.dto.BranchRequest;
import com.canteen.management.dto.BranchResponse;
import com.canteen.management.repository.BranchRepository;
import com.canteen.management.repository.OrganizationRepository;
import com.canteen.management.service.AuditLogService;
import com.canteen.management.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.entity.Branch;
import com.canteen.management.entity.Organization;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private com.canteen.management.repository.OrderRepository orderRepository;

    @Override
    public BranchResponse addBranch(BranchRequest request) {

        java.util.Optional<Branch> existingOpt = branchRepository.findByBranchCode(request.getBranchCode());

        Branch branch;
        if (existingOpt.isPresent()) {
            branch = existingOpt.get();
            if ("ACTIVE".equalsIgnoreCase(branch.getStatus())) {
                throw new RuntimeException("Branch Code already exists and is active");
            }
            branch.setStatus("ACTIVE");
        } else {
            branch = new Branch();
            branch.setBranchCode(request.getBranchCode());
        }

        Organization organization = organizationRepository
                .findById(request.getOrganizationId())
                .orElseThrow(() ->
                        new RuntimeException("Organization Not Found"));

        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setCity(request.getCity());
        branch.setState(request.getState());
        branch.setCountry(request.getCountry());
        branch.setPincode(request.getPincode());
        branch.setPhone(request.getPhone());
        branch.setEmail(request.getEmail());
        branch.setLogoUrl(request.getLogoUrl());
        branch.setOrganization(organization);

        Branch savedBranch = branchRepository.save(branch);

        auditLogService.saveLog(
                "SUPER_ADMIN",
                "SUPER_ADMIN",
                "CREATE_BRANCH",
                "Branch Created : " + savedBranch.getBranchName(),
                savedBranch.getOrganization().getId(),
                savedBranch.getId()
        );



        return new BranchResponse(
                savedBranch.getId(),
                savedBranch.getBranchCode(),
                savedBranch.getBranchName(),
                savedBranch.getAddress(),
                savedBranch.getCity(),
                savedBranch.getState(),
                savedBranch.getCountry(),
                savedBranch.getPincode(),
                savedBranch.getPhone(),
                savedBranch.getEmail(),
                savedBranch.getLogoUrl(),
                savedBranch.getStatus(),
                organization.getId(),
                organization.getName()
        );
    }


    @Override
    public List<BranchResponse> getAllBranches() {

        return branchRepository.findAll()
                .stream()
                .filter(branch -> !"INACTIVE".equalsIgnoreCase(branch.getStatus()))
                .map(branch -> {
                    BranchResponse response = new BranchResponse(
                            branch.getId(),
                            branch.getBranchCode(),
                            branch.getBranchName(),
                            branch.getAddress(),
                            branch.getCity(),
                            branch.getState(),
                            branch.getCountry(),
                            branch.getPincode(),
                            branch.getPhone(),
                            branch.getEmail(),
                            branch.getLogoUrl(),
                            branch.getStatus(),
                            branch.getOrganization().getId(),
                            branch.getOrganization().getName()
                    );
                    Double rev = orderRepository.getTotalRevenue(branch.getId());
                    response.setRevenue(rev != null ? rev : 0.0);
                    return response;
                })
                .toList();

    }

    @Override
    public List<BranchResponse> getBranchesByOrganization(Long organizationId) {

        return branchRepository
                .findByOrganizationId(organizationId)
                .stream()
                .filter(branch -> !"INACTIVE".equalsIgnoreCase(branch.getStatus()))
                .map(branch -> new BranchResponse(
                        branch.getId(),
                        branch.getBranchCode(),
                        branch.getBranchName(),
                        branch.getAddress(),
                        branch.getCity(),
                        branch.getState(),
                        branch.getCountry(),
                        branch.getPincode(),
                        branch.getPhone(),
                        branch.getEmail(),
                        branch.getLogoUrl(),
                        branch.getStatus(),
                        branch.getOrganization().getId(),
                        branch.getOrganization().getName()
                ))
                .toList();

    }

    @Override
    public BranchResponse getBranchById(Long id) {
        return null;
    }

    @Override
    public BranchResponse updateBranch(Long id, BranchRequest request) {
        return null;
    }

    @Override
    public String deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        branch.setStatus("INACTIVE");
        branchRepository.save(branch);
        
        auditLogService.saveLog(
                "SUPER_ADMIN",
                "SUPER_ADMIN",
                "DELETE_BRANCH",
                "Branch Deleted : " + branch.getBranchName(),
                branch.getOrganization().getId(),
                branch.getId()
        );
        
        return "Branch deleted successfully";
    }
}