package com.example.BackEnd.security.auth.service;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.Member.dto.SignupRequest;
import com.example.BackEnd.Member.repository.MemberRepository;
import com.example.BackEnd.Member.role.service.MemberRoleService;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.common.utils.JWTUtil;
import com.example.BackEnd.security.auth.dto.AuthTokens;
import com.example.BackEnd.security.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import lombok.*;

import static com.example.BackEnd.common.enums.error_codes.AuthErrorCode.*;
import static com.example.BackEnd.common.enums.error_codes.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SignupOTPEmailService signupOTPEmailService;
    private final RedisRefreshTokenService redisRefreshTokenService;
    private final MemberRoleService memberRoleService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Value("${aws.s3.basicProfile}")
    private String basicProfileUrl;

    @Transactional
    public void register(SignupRequest request) {
        validateUsernameNotDuplicated(request.getUsername());
        validateNicknameNotDuplicated(request.getNickname());
        validateEmailNotDuplicated(request.getEmail());
        validateEmailVerified(request.getEmail());

        Member member = createMember(request);
        memberRepository.save(member);
    }

    private void validateUsernameNotDuplicated(String username) {
        if (memberRepository.existsByUsernameIncludingDeleted(username)) {
            throw new BusinessException(USERNAME_ALREADY_EXISTS);
        }
    }

    private void validateNicknameNotDuplicated(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BusinessException(NICKNAME_ALREADY_EXISTS);
        }
    }

    private void validateEmailNotDuplicated(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateEmailVerified(String email) {
        if (signupOTPEmailService.isVerified(email)) {
            return;
        }
        throw new BusinessException(EMAIL_NOT_VERIFIED);
    }

    private Member createMember(SignupRequest request) {
        return Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImageUrl(basicProfileUrl)
                .build();
    }

    @Transactional
    public AuthTokens login(LoginRequest request) {
        Member existingMember = findMemberByUsernameOrThrow(request.getUsername());
        validatePassword(request.getPassword(), existingMember.getPassword());

        List<String> assignedRoles = memberRoleService.findRoleNamesByMemberId(existingMember.getId());
        AuthTokens authTokens = createTokens(existingMember.getUsername(), assignedRoles);
        rotateRefreshToken(existingMember, authTokens.getRefreshToken());

        return authTokens;
    }

    private void validatePassword(String password, String savedPassword) {
        if (passwordEncoder.matches(password, savedPassword)) {
            return;
        }
        throw new BusinessException(AUTH_INVALID_CREDENTIALS);
    }

    private AuthTokens createTokens(String username, List<String> assignedRoles) {
        String accessToken = jwtUtil.generateAccessToken(username, assignedRoles);
        String refreshToken = jwtUtil.generateRefreshToken(username, assignedRoles);
        return AuthTokens.of(accessToken, refreshToken);
    }

    @Transactional
    public AuthTokens reissueTokens(String refreshToken) {
        validateRefreshTokenJWT(refreshToken);

        String username = getUsernameFromRefreshToken(refreshToken);
        Member existingMember = findMemberByUsernameOrThrow(username);
        validateRefreshTokenInRedis(username, refreshToken);

        List<String> assignedRoles = memberRoleService.findRoleNamesByMemberId(existingMember.getId());
        AuthTokens newAuthTokens = createTokens(existingMember.getUsername(), assignedRoles);
        rotateRefreshToken(existingMember, newAuthTokens.getRefreshToken());

        return newAuthTokens;
    }

    private void validateRefreshTokenJWT(String refreshToken) {
        if (jwtUtil.isValidated(refreshToken)) {
            return;
        }
        throw new BusinessException(INVALID_REFRESH_TOKEN);
    }

    private String getUsernameFromRefreshToken(String refreshToken) {
        return jwtUtil.parseClaims(refreshToken).getSubject();
    }

    private void validateRefreshTokenInRedis(String username, String refreshToken) {
        if (redisRefreshTokenService.validateRefreshToken(username, refreshToken)) {
            return;
        }
        throw new BusinessException(INVALID_REFRESH_TOKEN);
    }

    private void rotateRefreshToken(Member member, String refreshToken) {
        redisRefreshTokenService.saveToken(member.getUsername(), refreshToken);
    }

    @Transactional
    public void logout(String accessToken) {
        validateAccessTokenJWT(accessToken);

        String username = jwtUtil.parseClaims(accessToken).getSubject();

        addAccessTokenAsBlacklisted(username, accessToken);
        invalidateRefreshToken(username);
    }

    private void validateAccessTokenJWT(String accessToken) {
        if (jwtUtil.isValidated(accessToken)) {
            return;
        }
        throw new BusinessException(INVALID_ACCESS_TOKEN);
    }

    private Member findMemberByUsernameOrThrow(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(AUTH_INVALID_CREDENTIALS));
    }

    private void addAccessTokenAsBlacklisted(String username, String accessToken) {
        redisRefreshTokenService.saveAccessTokenToBlacklist(username, accessToken);
    }

    private void invalidateRefreshToken(String username) {
        redisRefreshTokenService.deleteRefreshToken(username);
    }

}
