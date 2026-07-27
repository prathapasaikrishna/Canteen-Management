package com.canteen.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.canteen.management.dto.LoginRequest;
import com.canteen.management.dto.LoginResponse;
import com.canteen.management.dto.StudentRequest;
import com.canteen.management.dto.StudentResponse;
import com.canteen.management.service.StudentService;

import com.canteen.management.dto.UpdateProfileRequest;
import com.canteen.management.dto.UpdateProfileResponse;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public StudentResponse registerStudent(@Valid @RequestBody StudentRequest studentRequest) {

        return studentService.saveStudent(studentRequest);

    }

    @PutMapping("/profile")
    public UpdateProfileResponse updateProfile(
            @RequestBody UpdateProfileRequest request) {

        return studentService.updateProfile(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        return studentService.login(loginRequest);

    }

    @GetMapping("/{studentId}")
    public StudentResponse getStudentByStudentId(@PathVariable String studentId) {
        return studentService.getStudentByStudentId(studentId);
    }
}