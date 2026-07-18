package com.canteen.management.service;

import com.canteen.management.dto.LoginRequest;
import com.canteen.management.dto.LoginResponse;
import com.canteen.management.dto.StudentRequest;
import com.canteen.management.dto.StudentResponse;

public interface StudentService {

    StudentResponse saveStudent(StudentRequest studentRequest);

    LoginResponse login(LoginRequest loginRequest);
}