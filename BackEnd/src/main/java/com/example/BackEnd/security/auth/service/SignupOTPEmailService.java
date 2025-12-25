package com.example.BackEnd.security.auth.service;

import com.example.BackEnd.common.dto.EmailMessageDto;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.Member.service.EmailProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.*;

import static com.example.BackEnd.common.constants.RedisConstants.*;
import static com.example.BackEnd.common.enums.error_codes.AuthErrorCode.*;
import static com.example.BackEnd.common.enums.error_codes.CommonErrorCode.*;
import static com.example.BackEnd.common.utils.HashGenerator.*;
import static com.example.BackEnd.common.utils.OTPGenerator.*;
import static java.util.concurrent.TimeUnit.*;

@Service
@RequiredArgsConstructor
public class SignupOTPEmailService {

    private final StringRedisTemplate redisTemplate;
    private final EmailProducerService emailProducerService;

    @Value("${aws.ses.from}")
    private String from;

    public void saveOTPAndSendEmail(String email) {
        String OTPValueKey = createSignupOTPValueKey(email);
        String OTP = generate6DigitsOTP();

        try {
            saveOTP(OTPValueKey, OTP);
        } catch (Exception e) {
            throw new BusinessException(REDIS_CONNECTION_ERROR);
        }

        EmailMessageDto message = createEmailMessage(email, OTP);
        emailProducerService.sendEmail(message);
    }

    private String createSignupOTPValueKey(String email) {
        return SIGNUP_OTP_PREFIX + makeSha256(email);
    }

    private void saveOTP(String key, String OTP) {
        // 5분 동안 OTP 저장
        redisTemplate.opsForValue().set(key, OTP, 5, MINUTES);
    }

    private EmailMessageDto createEmailMessage(String email, String OTP) {
        return EmailMessageDto.builder()
                .to(email)
                .from(from)
                .subject("[Re:view Project] 회원가입을 위한 이메일 인증번호입니다.")
                .body("인증번호 : " + OTP + "\n\n위 인증번호를 사이트에 입력해 주시기 바랍니다.")
                .build();
    }

    public void verifyOTP(String email, String OTP) {
        String OTPValueKey = createSignupOTPValueKey(email);
        String OTPVerifiedStatusKey = createSignupOTPVerifiedStatusKey(email);
        String savedOTP = getSavedValue(OTPValueKey);

        if (isOTPMatched(OTP, savedOTP)) {
            try {
                markSignupOTPVerified(OTPVerifiedStatusKey);
                deleteSignupOTPValueKey(OTPValueKey);
            } catch (Exception e) {
                throw new BusinessException(REDIS_CONNECTION_ERROR);
            }
        } else {
            throw new BusinessException(SIGNUP_OTP_MISMATCH);
        }
    }

    private String createSignupOTPVerifiedStatusKey(String email) {
        return SIGNUP_VERIFIED_PREFIX + makeSha256(email);
    }

    private String getSavedValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private boolean isOTPMatched(String OTP, String savedOTP) {
        return savedOTP != null && savedOTP.equals(OTP);
    }

    private void markSignupOTPVerified(String OTPVerifiedStatusKey) {
        // 15분 동안 인증 상태 저장
        redisTemplate.opsForValue().set(OTPVerifiedStatusKey, OTP_VERIFIED, 15, MINUTES);
    }

    private void deleteSignupOTPValueKey(String OTPValueKey) {
        redisTemplate.delete(OTPValueKey);
    }

    public boolean isVerified(String email) {
        String OTPVerifiedStatusKey = createSignupOTPVerifiedStatusKey(email);
        String savedVerifiedStatus = getSavedValue(OTPVerifiedStatusKey);
        return savedVerifiedStatus.equals(OTP_VERIFIED);
    }

}
