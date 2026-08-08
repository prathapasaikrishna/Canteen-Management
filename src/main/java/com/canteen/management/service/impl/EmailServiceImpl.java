package com.canteen.management.service.impl;

import com.canteen.management.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private RestTemplate template;

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email:prathapadany@gmail.com}")
    private String senderEmail;

    @Override
    public void sendEmail(String to, String subject, String body) {
        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> request = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "Smart Foods");
        sender.put("email", senderEmail);

        request.put("sender", sender);
        request.put("to", new Object[]{
                Map.of("email", to)
        });

        request.put("subject", subject);
        request.put("htmlContent", body);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        try {
            template.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
        } catch (org.springframework.web.client.RestClientResponseException ex) {
            System.err.println("Email API call failed with status " + ex.getRawStatusCode() + ": " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            System.err.println("Email API call failed: " + ex.getMessage());
        }
    }

    @Override
    public void sendOtp(String toEmail, String otp) {
        String body =
                "<h2>Smart Foods Password Reset</h2>" +
                        "<p>Your OTP is:</p>" +
                        "<h1 style='color:#4CAF50'>" + otp + "</h1>" +
                        "<p>This OTP is valid for 5 minutes.</p>" +
                        "<br><b>Smart Foods Team</b>";

        sendEmail(
                toEmail,
                "Smart Foods - Password Reset OTP",
                body
        );
    }

    @Override
    public void sendRegistrationOtp(String toEmail, String otp) {
        String body =
                "<h2>Smart Foods Verification Code</h2>" +
                        "<p>Welcome to Smart Foods! Your registration verification OTP is:</p>" +
                        "<h1 style='color:#4CAF50'>" + otp + "</h1>" +
                        "<p>This OTP is valid for 5 minutes. Do not share it with anyone.</p>" +
                        "<br><b>Smart Foods Team</b>";

        sendEmail(
                toEmail,
                "Smart Foods - Registration Verification OTP",
                body
        );
    }

    @Override
    public void sendOrderInvoiceEmail(String toEmail, String studentName, String orderNumber, String foodName, int quantity, double totalPrice, String date) {
        if (toEmail == null || toEmail.isBlank()) return;

        String body = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px; background-color: #fcfcfc;\">" +
                "    <h2 style=\"color: #F59E0B; text-align: center; margin-bottom: 5px;\">SMART CANTEEN INVOICE</h2>" +
                "    <p style=\"text-align: center; font-size: 12px; color: #888; margin-top: 0;\">Thank you for dining with us!</p>" +
                "    <hr style=\"border: 0; border-top: 1px dashed #ccc; margin: 20px 0;\">" +
                "    <table style=\"width: 100%; font-size: 14px; line-height: 24px;\">" +
                "        <tr><td><strong>Order Number:</strong></td><td style=\"text-align: right;\">" + orderNumber + "</td></tr>" +
                "        <tr><td><strong>Date:</strong></td><td style=\"text-align: right;\">" + date + "</td></tr>" +
                "        <tr><td><strong>Customer Name:</strong></td><td style=\"text-align: right;\">" + studentName + "</td></tr>" +
                "        <tr><td><strong>Sent To:</strong></td><td style=\"text-align: right;\">" + toEmail + "</td></tr>" +
                "    </table>" +
                "    <hr style=\"border: 0; border-top: 1px solid #eee; margin: 20px 0;\">" +
                "    <table style=\"width: 100%; border-collapse: collapse; font-size: 14px;\">" +
                "        <thead>" +
                "            <tr style=\"border-bottom: 2px solid #eee; text-align: left;\">" +
                "                <th style=\"padding: 10px 0;\">Item Name</th>" +
                "                <th style=\"text-align: center; padding: 10px 0;\">Qty</th>" +
                "                <th style=\"text-align: right; padding: 10px 0;\">Total</th>" +
                "            </tr>" +
                "        </thead>" +
                "        <tbody>" +
                "            <tr style=\"border-bottom: 1px solid #eee;\">" +
                "                <td style=\"padding: 10px 0;\">" + foodName + "</td>" +
                "                <td style=\"text-align: center; padding: 10px 0;\">" + quantity + "</td>" +
                "                <td style=\"text-align: right; padding: 10px 0;\">₹" + totalPrice + "</td>" +
                "            </tr>" +
                "        </tbody>" +
                "    </table>" +
                "    <div style=\"text-align: right; margin-top: 20px; font-size: 16px;\">" +
                "        <strong>Total Amount Paid: <span style=\"color: #4CAF50;\">₹" + totalPrice + "</span></strong>" +
                "    </div>" +
                "    <hr style=\"border: 0; border-top: 1px dashed #ccc; margin: 25px 0;\">" +
                "    <p style=\"text-align: center; font-size: 12px; color: #888;\">If you have any questions about this receipt, please contact Canteen Admin.</p>" +
                "</div>";

        sendEmail(
                toEmail,
                "Smart Canteen Invoice - " + orderNumber,
                body
        );
    }
}