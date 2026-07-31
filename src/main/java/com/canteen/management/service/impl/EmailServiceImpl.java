package com.canteen.management.service.impl;

import com.canteen.management.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {

    }

    @Override
    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);

        message.setSubject("Smart Foods - Password Reset OTP");

        message.setText(
                "Hello,\n\n" +
                        "Your OTP for resetting your Smart Foods account password is:\n\n" +
                        otp +
                        "\n\nThis OTP is valid for 5 minutes.\n\n" +
                        "If you didn't request this, please ignore this email.\n\n" +
                        "Regards,\nSmart Foods Team"
        );

        mailSender.send(message);
    }
}