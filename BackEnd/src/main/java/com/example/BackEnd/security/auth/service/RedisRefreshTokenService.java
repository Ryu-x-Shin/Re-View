package com.example.BackEnd.security.auth.service;

import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.common.utils.JWTUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.*;

import static com.example.BackEnd.common.constants.RedisConstants.*;
import static com.example.BackEnd.common.enums.error_codes.CommonErrorCode.*;
import static com.example.BackEnd.common.utils.HashGenerator.*;
import static java.util.concurrent.TimeUnit.*;

@Service
@RequiredArgsConstructor
public class RedisRefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JWTUtil jwtUtil;

    public void saveToken(String username, String refreshToken) {
        String key = createTokenKey(username);

        try {
            saveRefreshToken(key, refreshToken);
        } catch (Exception e) {
            throw new BusinessException(REDIS_CONNECTION_ERROR);
        }
    }

    private String createTokenKey(String username) {
        return REFRESH_TOKEN_PREFIX + makeSha256(username);
    }

    private void saveRefreshToken(String tokenKey, String refreshToken) {
        redisTemplate.opsForValue().set(tokenKey, refreshToken, REFRESH_TOKEN_TTL, MILLISECONDS);
    }

    public boolean validateRefreshToken(String username, String refreshToken) {
        String key = createTokenKey(username);
        String savedRefreshToken = getSavedRefreshToken(key);
        return savedRefreshToken.equals(refreshToken);
    }

    private String getSavedRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveAccessTokenToBlacklist(String username, String accessToken) {
        String blacklistKey = createBlacklistKey(username);
        addBlacklist(blacklistKey, accessToken);
    }

    private String createBlacklistKey(String username) {
        return TOKEN_BLACKLIST_PREFIX + makeSha256(username);
    }

    private void addBlacklist(String blacklistKey, String accessToken) {
        long remainingMillis = jwtUtil.parseClaims(accessToken).getExpiration().getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(blacklistKey, accessToken, remainingMillis, MILLISECONDS);
    }

    public void deleteRefreshToken(String username) {
        String key = createTokenKey(username);
        redisTemplate.delete(key);
    }

}
