package com.canteen.management.service;

import com.canteen.management.dto.UserLoginRequest;
import com.canteen.management.dto.UserLoginResponse;
import com.canteen.management.dto.UserRegisterRequest;
import com.canteen.management.dto.UserResponse;


public interface UserService {


    UserResponse register(UserRegisterRequest request);


    UserLoginResponse login(UserLoginRequest request);


    UserResponse getUserById(Long id);

}