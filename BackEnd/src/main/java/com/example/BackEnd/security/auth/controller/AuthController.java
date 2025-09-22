package com.example.BackEnd.security.auth.controller;

import com.example.BackEnd.Member.dto.EmailTaskRequest;
import com.example.BackEnd.Member.dto.VerifyOtpRequest;
import com.example.BackEnd.Member.service.MemberService;
import com.example.BackEnd.Member.service.SignUpEmailOtpService;
import com.example.BackEnd.common.enums.error_codes.AuthError;
import com.example.BackEnd.common.enums.success_messages.AuthSuccess;
import com.example.BackEnd.security.dto.LoginRequest;
import com.example.BackEnd.security.dto.LoginResponse;
import com.example.BackEnd.security.auth.service.AuthService;
import com.example.BackEnd.common.Response.ApiResponse;
import com.example.BackEnd.common.enums.success_messages.GlobalSuccess;
import com.example.BackEnd.common.enums.success_messages.MemberSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;
    private final SignUpEmailOtpService signUpEmailOtpService;
    private final AuthService authService;

    // 인증번호 생성 및 전송
    @PostMapping("/send-otp")
    public ResponseEntity<?> generateAndSendOtp(@RequestBody @Valid EmailTaskRequest request) {
        String email = request.getEmail();
        memberService.existsByEmail(email);
        signUpEmailOtpService.generateAndSendOtp(email);
        return ApiResponse.success(GlobalSuccess.EMAIL_SEND_SUCCESS);
    }

    // 인증번호를 맞게 입력했는지 검증
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();
        boolean verified = signUpEmailOtpService.verifyOtp(email, otp);
        if (verified) {
            return ApiResponse.success(AuthSuccess.EMAIL_VERIFICATION_SUCCESS);
        }
        return ApiResponse.fail(AuthError.EMAIL_VERIFICATION_FAILED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);

        ResponseCookie refreshTokenCookie = getRefreshTokenCookie(response);

        // Access Token Response
        Map<String, String> responseBody = Map.of(
                "accessToken", response.getAccessToken()
        );

        // Refresh Token Response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ApiResponse.success(MemberSuccess.MEMBER_LOGIN_SUCCESS, responseBody, headers);
    }

    // Token Refresh
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken) {
        LoginResponse response = authService.refresh(refreshToken);

        ResponseCookie refreshTokenCookie = getRefreshTokenCookie(response);

        // Access Token Response
        Map<String, String> responseBody = Map.of(
                "accessToken", response.getAccessToken()
        );

        // Refresh Token Response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ApiResponse.success(AuthSuccess.TOKEN_REFRESH_SUCCESS, responseBody, headers);
    }

    // 로그 아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {

        String accessToken = authorizationHeader.substring(7);
        authService.logout(accessToken);

        return ApiResponse.success(MemberSuccess.MEMBER_LOGOUT_SUCCESS);
    }

    private static ResponseCookie getRefreshTokenCookie(LoginResponse response) {
        return ResponseCookie.from("refreshToken", response.getRefreshToken())
                .httpOnly(true)           // JS 접근 불가
                //.secure(true)             // HTTPS 환경일 때만 전송
                .path("/")                // 모든 경로에서 전송
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();
    }

}
