package com.example.BackEnd.common.utils;

import java.security.SecureRandom;

public class OTPGenerator {

    private OTPGenerator() {}

    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generate6DigitsOTP() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

}
