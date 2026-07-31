package com.canteen.management.service.impl;

import com.canteen.management.service.EmailService;
import org.hibernate.sql.Template;
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

    @Override
    public void sendEmail(String to, String subject, String body) {

        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> request = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "Smart Foods");
        sender.put("email", "prathapadany@gmail.com"); // <-- Verify చేసిన sender email

        request.put("sender", sender);

        request.put("to", new Object[]{
                Map.of("email", to)
        });

        request.put("subject", subject);
        request.put("htmlContent", body);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        template.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
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
}