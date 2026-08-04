package com.canteen.management.service.impl;

import com.canteen.management.dto.BranchRequest;
import com.canteen.management.dto.BranchResponse;
import com.canteen.management.repository.BranchRepository;
import com.canteen.management.repository.OrganizationRepository;
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
    private OrganizationRepository organizationRepository;

    @Override
    public BranchResponse addBranch(BranchRequest request) {

        if (branchRepository.existsByBranchCode(request.getBranchCode())) {
            throw new RuntimeException("Branch Code already exists");
        }

        Organization organization = organizationRepository
                .findById(request.getOrganizationId())
                .orElseThrow(() ->
                        new RuntimeException("Organization Not Found"));

        Branch branch = new Branch();

        branch.setBranchCode(request.getBranchCode());
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

        Branch saved = branchRepository.save(branch);

        return new BranchResponse(
                saved.getId(),
                saved.getBranchCode(),
                saved.getBranchName(),
                saved.getAddress(),
                saved.getCity(),
                saved.getState(),
                saved.getCountry(),
                saved.getPincode(),
                saved.getPhone(),
                saved.getEmail(),
                saved.getLogoUrl(),
                saved.getStatus(),
                organization.getId(),
                organization.getName()
        );
    }


    @Override
    public List<BranchResponse> getAllBranches() {

        return branchRepository.findAll()
                .stream()
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
    public List<BranchResponse> getBranchesByOrganization(Long organizationId) {

        return branchRepository
                .findByOrganizationId(organizationId)
                .stream()
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
        return null;
    }
}