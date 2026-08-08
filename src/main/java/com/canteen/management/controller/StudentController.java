package com.canteen.management.controller;

import com.canteen.management.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import com.canteen.management.dto.ChangePasswordRequest;
import com.canteen.management.dto.ApiResponse;
import com.canteen.management.service.StudentService;
import com.canteen.management.service.EmailService;
import com.canteen.management.service.OtpService;
import com.canteen.management.service.CloudinaryService;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.entity.Student;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/register-otp")
    public ApiResponse sendRegisterOtp(@RequestParam("email") String email) {
        if (studentRepository.existsByEmail(email)) {
            return new ApiResponse("Email already exists");
        }
        String otp = otpService.generateOtp(email);
        emailService.sendRegistrationOtp(email, otp);
        return new ApiResponse("OTP Sent Successfully");
    }

    @PostMapping("/register")
    public StudentResponse registerStudent(@Valid @RequestBody StudentRequest studentRequest) {
        return studentService.saveStudent(studentRequest);
    }

    @PutMapping("/profile")
    public UpdateProfileResponse updateProfile(@RequestBody UpdateProfileRequest request) {
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

    @PostMapping("/forgot-password")
    public ApiResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail());
        if (student == null) {
            return new ApiResponse("Email Not Registered");
        }
        String otp = otpService.generateOtp(request.getEmail());
        emailService.sendOtp(request.getEmail(), otp);
        return new ApiResponse("OTP Sent Successfully");
    }

    @PostMapping("/verify-otp")
    public ApiResponse verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean valid = otpService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );
        if (valid) {
            return new ApiResponse("OTP Verified");
        }
        return new ApiResponse("Invalid OTP");
    }

    @PostMapping("/reset-password")
    public ApiResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail());
        if (student == null) {
            return new ApiResponse("User Not Found");
        }
        student.setPassword(request.getNewPassword());
        studentRepository.save(student);
        return new ApiResponse("Password Reset Successfully");
    }

    @PostMapping("/change-password")
    public ApiResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return studentService.changePassword(request);
    }

    @PostMapping("/save-token")
    public ApiResponse saveToken(@RequestBody SaveFcmTokenRequest request) {
        return studentService.saveFcmToken(request);
    }

    @PostMapping("/google-login")
    public LoginResponse googleLogin(@RequestBody GoogleLoginRequest request) {
        return studentService.googleLogin(request);
    }

    @GetMapping("/branch/{branchId}")
    public java.util.List<Student> getStudentsByBranch(@PathVariable Long branchId) {
        return studentRepository.findByBranchIdAndRole(branchId, "STUDENT");
    }

    @PostMapping(value = "/profile/image/{studentId}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentResponse> updateProfileImage(
            @PathVariable("studentId") String studentId,
            @RequestParam("image") MultipartFile image) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        String imageUrl = cloudinaryService.uploadImage(image);
        if (imageUrl == null || imageUrl.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        student.setProfileUrl(imageUrl);
        studentRepository.save(student);

        StudentResponse res = studentService.getStudentByStudentId(studentId);
        res.setProfileUrl(imageUrl);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/all")
    public java.util.List<Student> getAllStudents() {
        return studentRepository.findAll().stream()
                .filter(s -> "STUDENT".equalsIgnoreCase(s.getRole()))
                .toList();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable("id") Integer id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.delete(student);
        return ResponseEntity.ok(new ApiResponse("Customer deleted successfully"));
    }
}