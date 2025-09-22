package com.example.BackEnd.security.auth.service;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.Member.repository.MemberRepository;
import com.example.BackEnd.common.enums.error_codes.GlobalError;
import com.example.BackEnd.security.dto.LoginRequest;
import com.example.BackEnd.security.dto.LoginResponse;
import com.example.BackEnd.common.enums.error_codes.AuthError;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.common.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.example.BackEnd.common.utils.JWTUtil.BLACKLIST_PREFIX;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;
    private final RedisRefreshTokenService redisRefreshTokenService;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByUsernameAndDeletedFalse(request.getUsername())
                .orElseThrow(() -> new BusinessException(AuthError.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(AuthError.AUTH_INVALID_CREDENTIALS);
        }

        String accessToken = jwtUtil.generateAccessToken(member.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(member.getUsername());

        // Refresh Token Rotation
        member.changeRefreshTokenHash(passwordEncoder.encode(refreshToken));
        redisRefreshTokenService.saveToken(request.getUsername(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public LoginResponse refresh(String refreshToken) {

        // JWT 자체를 검증
        if (!jwtUtil.isValidated(refreshToken)) {
            throw new BusinessException(AuthError.INVALID_REFRESH_TOKEN);
        }

        // 해당 Refresh Token의 소유자인 Member가 존재하는지 확인
        String username = jwtUtil.parseClaims(refreshToken).getSubject();
        Member member = memberRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new BusinessException(AuthError.INVALID_REFRESH_TOKEN));

        // Redis에 저장된 Token과 일치하는지 확인
        String storedToken = redisRefreshTokenService.getToken(username);
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new BusinessException(AuthError.INVALID_REFRESH_TOKEN);
        }

        // DB에 저장된 Token과 일치하는지 확인
        if (!passwordEncoder.matches(refreshToken, member.getRefreshTokenHash())) {
            throw new BusinessException(AuthError.INVALID_REFRESH_TOKEN);
        }

        // Refresh Token Rotation
        String newRefreshToken = jwtUtil.generateRefreshToken(username);
        member.changeRefreshTokenHash(passwordEncoder.encode(newRefreshToken));
        redisRefreshTokenService.saveToken(username, newRefreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(username);
        return new LoginResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(String accessToken) {

        String username = jwtUtil.parseClaims(accessToken).getSubject();

        // DB에서 Refresh Token null 처리
        Member member = memberRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new BusinessException(AuthError.MEMBER_NOT_FOUND));
        member.changeRefreshTokenHash(null);

        // Access Token 블랙리스트 등록
        String jti = jwtUtil.parseClaims(accessToken).getId();
        long remainTimeMillis = jwtUtil.parseClaims(accessToken).getExpiration().getTime() - System.currentTimeMillis();
        try {
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + jti, "true", remainTimeMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new BusinessException(GlobalError.REDIS_CONNECTION_ERROR);
        }
    }

}
