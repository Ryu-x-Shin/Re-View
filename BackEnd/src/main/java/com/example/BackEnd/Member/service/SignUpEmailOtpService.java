package com.example.BackEnd.Member.service;

import com.example.BackEnd.common.dto.EmailMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import com.example.BackEnd.common.utils.OtpGenerator;
import com.example.BackEnd.common.utils.HashGenerator;

import static com.example.BackEnd.config.RedisConfig.OTP_PREFIX;
import static com.example.BackEnd.config.RedisConfig.VERIFIED_PREFIX;

@Service
@RequiredArgsConstructor
public class SignUpEmailOtpService {

    private final StringRedisTemplate redisTemplate;
    private final EmailProducerService emailProducerService;

    @Value("${aws.ses.from}")
    private String from;

    public void generateAndSendOtp(String email) {
        String hashedEmail = HashGenerator.makeSha256(email);
        String otp = OtpGenerator.generate6DigitsOtp();
        try {
            redisTemplate.opsForValue().set(OTP_PREFIX + hashedEmail, otp, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EmailMessageDto message = EmailMessageDto.builder()
                .to(email)
                .from(from)
                .subject("[Re:view Project] 회원가입을 위한 이메일 인증번호입니다.")
                .body("인증번호 : " + otp + "\n위 인증번호를 사이트에 입력해 주시기 바랍니다.")
                .build();

        emailProducerService.sendEmail(message);
    }

    public boolean verifyOtp(String email, String otp) {
        String hashedEmail = HashGenerator.makeSha256(email);
        String savedOtp = redisTemplate.opsForValue().get(OTP_PREFIX + hashedEmail);
        if (savedOtp != null && savedOtp.equals(otp)) {
            redisTemplate.opsForValue().set(VERIFIED_PREFIX + hashedEmail, "true", 15, TimeUnit.MINUTES);
            redisTemplate.delete(OTP_PREFIX + hashedEmail);
            return true;
        }
        return false;
    }

    public boolean isVerified(String email) {
        String hashedEmail = HashGenerator.makeSha256(email);
        return "true".equals(redisTemplate.opsForValue().get(VERIFIED_PREFIX + hashedEmail));
    }
}
