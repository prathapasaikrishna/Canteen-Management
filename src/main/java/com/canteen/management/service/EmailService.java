package com.canteen.management.service;

public interface EmailService {

    void sendEmail(
            String to,
            String subject,
            String body
    );

    void sendOtp(String email, String otp);
}