package com.canteen.management.service;

public interface EmailService {

    void sendEmail(
            String to,
            String subject,
            String body
    );

    void sendOtp(String email, String otp);

    void sendRegistrationOtp(String email, String otp);

    void sendOrderInvoiceEmail(String toEmail, String studentName, String orderNumber, String foodName, int quantity, double totalPrice, String date);
}