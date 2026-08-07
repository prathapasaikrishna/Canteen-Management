package com.canteen.management.service.impl;

import com.canteen.management.dto.*;
import com.canteen.management.entity.Student;
import com.canteen.management.entity.Wallet;
import com.canteen.management.exception.EmailAlreadyExistsException;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.repository.WalletRepository;
import com.canteen.management.security.JwtUtil;
import com.canteen.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JwtUtil jwtUtil;



    @Override
    public StudentResponse saveStudent(StudentRequest studentRequest) {
        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Student student = new Student();
        student.setStudentId(studentRequest.getStudentId());
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());

        // BCrypt Password Encryption
        student.setPassword(
                passwordEncoder.encode(studentRequest.getPassword())
        );
        student.setDepartment(studentRequest.getDepartment());
        student.setMobileNumber(studentRequest.getMobileNumber());
        student.setYear(studentRequest.getYear());
        student.setRole(studentRequest.getRole());
        student.setCanteenId(studentRequest.getCanteenId());

        student.setOrganizationId(studentRequest.getOrganizationId());
        student.setBranchId(studentRequest.getBranchId());
        student.setAccountStatus("ACTIVE");
        student.setUserType("CUSTOMER");

        
        Student savedStudent = studentRepository.save(student);

        Wallet wallet = new Wallet();

        wallet.setStudentId(savedStudent.getStudentId());
        wallet.setBalance(100.0); // Welcome Bonus
        walletRepository.save(wallet);


        StudentResponse response = new StudentResponse();
        response.setId(savedStudent.getId());
        response.setStudentId(savedStudent.getStudentId());
        response.setName(savedStudent.getName());
        response.setEmail(savedStudent.getEmail());
        response.setDepartment(savedStudent.getDepartment());
        response.setMobileNumber(savedStudent.getMobileNumber());
        response.setYear(savedStudent.getYear());
        response.setRole(savedStudent.getRole());
        response.setCanteenId(savedStudent.getCanteenId());
        response.setMessage("Student Registered Successfully");
        response.setOrganizationId(savedStudent.getOrganizationId());
        response.setBranchId(savedStudent.getBranchId());
        response.setUserType(savedStudent.getUserType());
        response.setAccountStatus(savedStudent.getAccountStatus());
        return response;

    }



    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Student student = studentRepository.findByEmail(
                loginRequest.getEmail()
        );

        if (student != null &&
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        student.getPassword()
                )) {

            String status = student.getAccountStatus() != null ? student.getAccountStatus() : "ACTIVE";
            if (!"ACTIVE".equalsIgnoreCase(status)) {
                return new LoginResponse(
                        "Account is " + status,
                        ""
                );
            }
            
            // Save FCM Token if available
            if (loginRequest.getFcmToken() != null &&
                    !loginRequest.getFcmToken().isBlank()) {
                System.out.println("==================================");
                System.out.println("LOGIN API RECEIVED FCM TOKEN");
                System.out.println("Student = " + student.getStudentId());
                System.out.println("Token = " + loginRequest.getFcmToken());
                System.out.println("==================================");
                student.setFcmToken(loginRequest.getFcmToken());
                studentRepository.save(student);
            }

            // Generate JWT Token
            String token = jwtUtil.generateToken(student.getEmail());

            LoginResponse response = new LoginResponse(
                    "Login Successful",
                    token
            );

            response.setStudentId(student.getStudentId());
            response.setStudentName(student.getName());
            response.setEmail(student.getEmail());
            response.setMobileNumber(student.getMobileNumber());
            response.setRole(student.getRole());
            response.setCanteenId(student.getCanteenId());

            response.setOrganizationId(student.getOrganizationId());
            response.setBranchId(student.getBranchId());

            return response;
        } else {
            return new LoginResponse(
                    "Invalid Email or Password",
                    ""
            );
        }
    }

    @Override
    public UpdateProfileResponse updateProfile(UpdateProfileRequest request) {
        Optional<Student> optionalStudent =
                studentRepository.findByStudentId(request.getStudentId());

        if (optionalStudent.isEmpty()) {
            return new UpdateProfileResponse("Student Not Found");
        }

        Student student = optionalStudent.get();
        student.setName(request.getName());
        student.setMobileNumber(request.getMobileNumber());

        studentRepository.save(student);

        return new UpdateProfileResponse(
                "Profile Updated Successfully"
        );
    }

    @Override
    public StudentResponse getStudentByStudentId(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setStudentId(student.getStudentId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setDepartment(student.getDepartment());
        response.setMobileNumber(student.getMobileNumber());
        response.setYear(student.getYear());
        response.setRole(student.getRole());
        response.setCanteenId(student.getCanteenId());
        response.setMessage("Success");
        response.setOrganizationId(student.getOrganizationId());
        response.setBranchId(student.getBranchId());
        response.setUserType(student.getUserType());
        response.setAccountStatus(student.getAccountStatus());
        return response;    }

    @Override
    public ApiResponse changePassword(ChangePasswordRequest request) {
        Student student = studentRepository
                .findByStudentId(request.getStudentId())
                .orElse(null);

        if (student == null) {
            return new ApiResponse("Student Not Found");
        }

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                student.getPassword())) {
            return new ApiResponse("Old Password Incorrect");
        }

        student.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        studentRepository.save(student);

        return new ApiResponse("Password Changed Successfully");
    }

    @Override
    public ApiResponse saveFcmToken(SaveFcmTokenRequest request) {
        Student student = studentRepository
                .findByStudentId(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student Not Found"));

        student.setFcmToken(request.getToken());
        studentRepository.save(student);

        return new ApiResponse("FCM Token Saved");
    }

    @Override
    public LoginResponse googleLogin(GoogleLoginRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail());

        if (student == null) {
            student = new Student();
            student.setStudentId("STU" + System.currentTimeMillis());
            
            // Set password default
            student.setPassword(passwordEncoder.encode("GOOGLE_LOGIN"));
            student.setRole("STUDENT");
            student.setCanteenId("C001");
        }

        String name = request.getName();
        if (name == null || name.isBlank()) {
            name = student.getName();
        }
        if (name == null || name.isBlank()) {
            name = request.getEmail().split("@")[0];
        }
        student.setName(name);
        student.setEmail(request.getEmail());

        // Satisfy non-blank / pattern constraints dynamically for both new and existing users
        if (student.getDepartment() == null || student.getDepartment().isBlank()) {
            student.setDepartment("Google User");
        }
        if (student.getYear() == null || student.getYear().isBlank()) {
            student.setYear("N/A");
        }
        if (student.getMobileNumber() == null || !student.getMobileNumber().matches("^[0-9]{10}$")) {
            student.setMobileNumber("9999999999");
        }

        if (request.getFcmToken() != null && !request.getFcmToken().isBlank()) {
            student.setFcmToken(request.getFcmToken());
        }

        studentRepository.save(student);

        String token = jwtUtil.generateToken(student.getEmail());

        LoginResponse response = new LoginResponse("Google Login Successful", token);
        response.setStudentId(student.getStudentId());
        response.setStudentName(student.getName());
        response.setEmail(student.getEmail());
        response.setMobileNumber(student.getMobileNumber());
        response.setRole(student.getRole());
        response.setCanteenId(student.getCanteenId());

        response.setOrganizationId(student.getOrganizationId());
        response.setBranchId(student.getBranchId());

        return response;
    }
}