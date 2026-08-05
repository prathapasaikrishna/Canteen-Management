package com.canteen.management.service.impl;

import com.canteen.management.dto.*;
import com.canteen.management.entity.BranchAdmin;
import com.canteen.management.repository.BranchAdminRepository;
import com.canteen.management.service.BranchAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchAdminServiceImpl implements BranchAdminService {

    @Autowired
    private BranchAdminRepository repository;

    @Override
    public BranchAdminResponse addBranchAdmin(BranchAdminRequest request) {

        BranchAdmin admin = new BranchAdmin();

        admin.setOrganizationId(request.getOrganizationId());
        admin.setBranchId(request.getBranchId());
        admin.setAdminName(request.getAdminName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setMobile(request.getMobile());
        admin.setRole(request.getRole());
        admin.setStatus(request.getStatus());

        repository.save(admin);

        BranchAdminResponse response = new BranchAdminResponse();

        response.setId(admin.getId());
        response.setOrganizationId(admin.getOrganizationId());
        response.setBranchId(admin.getBranchId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setMobile(admin.getMobile());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Branch Admin Added Successfully");

        return response;
    }

    @Override
    public BranchAdminResponse updateBranchAdmin(Long id,
                                                 BranchAdminRequest request) {

        BranchAdmin admin = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Branch Admin Not Found"));

        admin.setOrganizationId(request.getOrganizationId());
        admin.setBranchId(request.getBranchId());
        admin.setAdminName(request.getAdminName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setMobile(request.getMobile());
        admin.setRole(request.getRole());
        admin.setStatus(request.getStatus());

        repository.save(admin);

        BranchAdminResponse response = new BranchAdminResponse();

        response.setId(admin.getId());
        response.setOrganizationId(admin.getOrganizationId());
        response.setBranchId(admin.getBranchId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setMobile(admin.getMobile());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Branch Admin Updated Successfully");

        return response;
    }

    @Override
    public String deleteBranchAdmin(Long id) {

        repository.deleteById(id);

        return "Branch Admin Deleted Successfully";
    }

    @Override
    public List<BranchAdminResponse> getAllBranchAdmins() {

        List<BranchAdmin> admins = repository.findAll();

        List<BranchAdminResponse> responseList = new ArrayList<>();

        for (BranchAdmin admin : admins) {

            BranchAdminResponse response = new BranchAdminResponse();

            response.setId(admin.getId());
            response.setOrganizationId(admin.getOrganizationId());
            response.setBranchId(admin.getBranchId());
            response.setAdminName(admin.getAdminName());
            response.setEmail(admin.getEmail());
            response.setMobile(admin.getMobile());
            response.setRole(admin.getRole());
            response.setStatus(admin.getStatus());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<BranchAdminResponse> getBranchAdminsByBranch(Long branchId) {

        List<BranchAdmin> admins =
                repository.findByBranchId(branchId);

        List<BranchAdminResponse> list = new ArrayList<>();

        for (BranchAdmin admin : admins) {

            BranchAdminResponse response = new BranchAdminResponse();

            response.setId(admin.getId());
            response.setOrganizationId(admin.getOrganizationId());
            response.setBranchId(admin.getBranchId());
            response.setAdminName(admin.getAdminName());
            response.setEmail(admin.getEmail());
            response.setMobile(admin.getMobile());
            response.setRole(admin.getRole());
            response.setStatus(admin.getStatus());

            list.add(response);
        }

        return list;
    }

    @Override
    public BranchAdminLoginResponse login(
            BranchAdminLoginRequest request) {

        BranchAdmin admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        BranchAdminLoginResponse response =
                new BranchAdminLoginResponse();

        response.setId(admin.getId());
        response.setOrganizationId(admin.getOrganizationId());
        response.setBranchId(admin.getBranchId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Login Successful");

        return response;
    }

}