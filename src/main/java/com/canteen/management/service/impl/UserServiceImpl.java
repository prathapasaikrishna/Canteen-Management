package com.canteen.management.service.impl;


import com.canteen.management.dto.*;
import com.canteen.management.entity.User;
import com.canteen.management.repository.UserRepository;
import com.canteen.management.security.JwtUtil;
import com.canteen.management.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtUtil jwtUtil;



    @Override
    public UserResponse register(UserRegisterRequest request) {


        if(userRepository.existsByEmail(request.getEmail())){

            throw new RuntimeException("Email already exists");

        }


        User user = new User();


        user.setName(request.getName());

        user.setEmail(request.getEmail());


        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        user.setMobileNumber(
                request.getMobileNumber()
        );


        user.setRole(
                request.getRole()
        );


        user.setOrganizationId(
                request.getOrganizationId()
        );


        user.setBranchId(
                request.getBranchId()
        );


        user.setUserType(
                request.getUserType()
        );


        user.setAccountStatus("ACTIVE");



        User savedUser =
                userRepository.save(user);



        UserResponse response =
                new UserResponse();


        response.setId(savedUser.getId());

        response.setName(savedUser.getName());

        response.setEmail(savedUser.getEmail());

        response.setMobileNumber(
                savedUser.getMobileNumber()
        );

        response.setRole(savedUser.getRole());

        response.setOrganizationId(
                savedUser.getOrganizationId()
        );

        response.setBranchId(
                savedUser.getBranchId()
        );

        response.setUserType(
                savedUser.getUserType()
        );

        response.setAccountStatus(
                savedUser.getAccountStatus()
        );


        response.setMessage(
                "User Registered Successfully"
        );


        return response;

    }




    @Override
    public UserLoginResponse login(UserLoginRequest request) {


        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElse(null);



        if(user == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )){


            UserLoginResponse response =
                    new UserLoginResponse();

            response.setMessage(
                    "Invalid Email or Password"
            );

            return response;

        }



        String token =
                jwtUtil.generateToken(
                        user.getEmail()
                );



        UserLoginResponse response =
                new UserLoginResponse();



        response.setMessage(
                "Login Successful"
        );


        response.setToken(token);


        response.setId(user.getId());

        response.setName(user.getName());

        response.setEmail(user.getEmail());

        response.setRole(user.getRole());


        response.setOrganizationId(
                user.getOrganizationId()
        );


        response.setBranchId(
                user.getBranchId()
        );


        response.setUserType(
                user.getUserType()
        );


        return response;

    }





    @Override
    public UserResponse getUserById(Long id) {


        User user =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("User Not Found")
                        );



        UserResponse response =
                new UserResponse();


        response.setId(user.getId());

        response.setName(user.getName());

        response.setEmail(user.getEmail());

        response.setMobileNumber(
                user.getMobileNumber()
        );

        response.setRole(
                user.getRole()
        );

        response.setOrganizationId(
                user.getOrganizationId()
        );

        response.setBranchId(
                user.getBranchId()
        );

        response.setUserType(
                user.getUserType()
        );

        response.setAccountStatus(
                user.getAccountStatus()
        );


        response.setMessage("Success");


        return response;

    }

}