package com.canteen.management.dto;

public class GoogleLoginResponse {

    private String message;
    private String token;
    private String studentId;
    private String studentName;
    private String email;
    private String mobileNumber;
    private String role;
    private String canteenId;

    public GoogleLoginResponse() {
    }

    public GoogleLoginResponse(
            String message,
            String token,
            String studentId,
            String studentName,
            String email,
            String mobileNumber,
            String role,
            String canteenId) {

        this.message = message;
        this.token = token;
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.canteenId = canteenId;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }
}