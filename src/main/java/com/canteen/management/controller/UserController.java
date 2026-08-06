package com.canteen.management.controller;


import com.canteen.management.dto.UserLoginRequest;
import com.canteen.management.dto.UserLoginResponse;
import com.canteen.management.dto.UserRegisterRequest;
import com.canteen.management.dto.UserResponse;
import com.canteen.management.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public UserResponse register(
            @RequestBody UserRegisterRequest request
    ){

        return userService.register(request);

    }




    @PostMapping("/login")
    public UserLoginResponse login(
            @RequestBody UserLoginRequest request
    ){

        return userService.login(request);

    }




    @GetMapping("/{id}")
    public UserResponse getUser(
            @PathVariable Long id
    ){

        return userService.getUserById(id);

    }


}