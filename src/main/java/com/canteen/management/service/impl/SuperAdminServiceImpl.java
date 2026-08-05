package com.canteen.management.service.impl;

import com.canteen.management.dto.SuperAdminLoginRequest;
import com.canteen.management.dto.SuperAdminLoginResponse;
import com.canteen.management.entity.SuperAdmin;
import com.canteen.management.repository.SuperAdminRepository;
import com.canteen.management.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    private SuperAdminRepository repository;

    @Override
    public SuperAdminLoginResponse login(SuperAdminLoginRequest request) {

        System.out.println("Email = " + request.getEmail());
        System.out.println("Password = " + request.getPassword());

        SuperAdmin admin = repository.findByEmail(request.getEmail()).orElse(null);

        System.out.println("Request Email : " + request.getEmail());
        System.out.println("Admin : " + admin);

        if(admin == null){
            throw new RuntimeException("Admin Not Found");
        }

        System.out.println("DB Password : " + admin.getPassword());

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Wrong Password");
        }

        SuperAdminLoginResponse response = new SuperAdminLoginResponse();

        response.setId(admin.getId());
        response.setName(admin.getName());
        response.setEmail(admin.getEmail());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Login Successful");

        return response;
    }
}