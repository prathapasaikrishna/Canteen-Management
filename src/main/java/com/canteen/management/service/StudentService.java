package com.canteen.management.service;

import com.canteen.management.dto.LoginRequest;
import com.canteen.management.dto.LoginResponse;
import com.canteen.management.dto.StudentRequest;
import com.canteen.management.dto.StudentResponse;
import com.canteen.management.dto.UpdateProfileRequest;
import com.canteen.management.dto.UpdateProfileResponse;

public interface StudentService {

    StudentResponse saveStudent(StudentRequest studentRequest);

    LoginResponse login(LoginRequest loginRequest);

    UpdateProfileResponse updateProfile(UpdateProfileRequest request);

    StudentResponse getStudentByStudentId(String studentId);
}