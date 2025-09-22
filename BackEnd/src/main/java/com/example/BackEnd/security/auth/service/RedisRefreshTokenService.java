package com.example.BackEnd.security.auth.service;

import com.example.BackEnd.common.enums.error_codes.GlobalError;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.common.utils.HashGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.example.BackEnd.common.utils.JWTUtil.*;

@Service
@RequiredArgsConstructor
public class RedisRefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveToken(String username, String refreshToken) {
        String hashedUsername = HashGenerator.makeSha256(username);
        try {
            redisTemplate.opsForValue().set(
                    REFRESH_TOKEN_PREFIX + hashedUsername, refreshToken, refreshTokenTTL, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new BusinessException(GlobalError.REDIS_CONNECTION_ERROR);
        }
    }

    public String getToken(String username) {
        String hashedUsername = HashGenerator.makeSha256(username);
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + hashedUsername);
    }

}
