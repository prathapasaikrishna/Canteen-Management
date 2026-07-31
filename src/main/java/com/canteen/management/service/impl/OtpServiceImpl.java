package com.canteen.management.service.impl;

import com.canteen.management.service.OtpService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {

    private final Map<String, String> otpMap = new ConcurrentHashMap<>();
    private final Map<String, Long> expiryMap = new ConcurrentHashMap<>();

    @Override
    public String generateOtp(String email) {

        String otp = String.format("%06d",
                new Random().nextInt(999999));

        otpMap.put(email, otp);
        System.out.println("GENERATED OTP : " + otp);
        System.out.println("OTP EMAIL : " + email);

        expiryMap.put(
                email,
                System.currentTimeMillis() + (5 * 60 * 1000)
        );

        return otp;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {

        System.out.println("========== OTP VERIFY ==========");
        System.out.println("EMAIL RECEIVED : " + email);
        System.out.println("OTP RECEIVED   : " + otp);
        System.out.println("OTP STORED     : " + otpMap.get(email));
        System.out.println("================================");

        if (!otpMap.containsKey(email)) {
            return false;
        }

        if (System.currentTimeMillis() > expiryMap.get(email)) {

            otpMap.remove(email);
            expiryMap.remove(email);

            return false;
        }

        boolean valid = otp.equals(otpMap.get(email));

        if(valid){
            otpMap.remove(email);
            expiryMap.remove(email);
        }

        return valid;
    }
}