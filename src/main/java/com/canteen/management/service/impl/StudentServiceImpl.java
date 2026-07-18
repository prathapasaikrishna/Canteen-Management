package com.canteen.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canteen.management.dto.LoginRequest;
import com.canteen.management.dto.LoginResponse;
import com.canteen.management.dto.StudentRequest;
import com.canteen.management.dto.StudentResponse;
import com.canteen.management.entity.Student;
import com.canteen.management.exception.EmailAlreadyExistsException;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentResponse saveStudent(StudentRequest studentRequest) {

        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Student student = new Student();

        student.setStudentId(studentRequest.getStudentId());
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setPassword(studentRequest.getPassword());
        student.setDepartment(studentRequest.getDepartment());
        student.setMobileNumber(studentRequest.getMobileNumber());
        student.setYear(studentRequest.getYear());
        student.setRole(studentRequest.getRole());

        Student savedStudent = studentRepository.save(student);

        return new StudentResponse(
                savedStudent.getId(),
                savedStudent.getStudentId(),
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getDepartment(),
                savedStudent.getMobileNumber(),
                savedStudent.getYear(),
                savedStudent.getRole(),
                "Student Registered Successfully"
        );
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Student student = studentRepository.findByEmailAndPassword(
                loginRequest.getEmail(),
                loginRequest.getPassword());

        if (student != null) {
            return new LoginResponse("Login Successful", "");
        } else {
            return new LoginResponse("Invalid Email or Password", "");
        }
    }
}