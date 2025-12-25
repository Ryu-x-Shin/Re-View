package com.example.BackEnd.security.auth.controller;

import com.example.BackEnd.Member.dto.*;
import com.example.BackEnd.Member.service.MemberService;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.security.auth.dto.AuthTokens;
import com.example.BackEnd.security.dto.*;
import com.example.BackEnd.security.auth.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import lombok.*;

import static com.example.BackEnd.common.Response.ApiResponse.*;
import static com.example.BackEnd.common.constants.CookieConstants.*;
import static com.example.BackEnd.common.constants.HTTPConstants.*;
import static com.example.BackEnd.common.enums.error_codes.AuthErrorCode.*;
import static com.example.BackEnd.common.enums.success_messages.AuthSuccessCode.*;
import static com.example.BackEnd.common.enums.success_messages.CommonSuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final SignupOTPEmailService signupOTPEmailService;

    @PostMapping("/signup/send-otp")
    public ResponseEntity<?> sendSignupOTPEmail(@RequestBody @Valid EmailOTPRequest request) {
        memberService.checkEmailDuplication(request.getEmail());
        signupOTPEmailService.saveOTPAndSendEmail(request.getEmail());
        return success(EMAIL_SEND_SUCCESS);
    }

    @PostMapping("/signup/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody @Valid VerifyOTPRequest request) {
        signupOTPEmailService.verifyOTP(request.getEmail(), request.getOTP());
        return success(EMAIL_VERIFICATION_SUCCESS);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        authService.register(request);
        return success(MEMBER_SIGNUP_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        AuthTokens loginTokens = authService.login(request);

        AccessTokenResponse accessTokenResponse = AccessTokenResponse.of(loginTokens.getAccessToken());
        ResponseCookie refreshTokenCookie = createHttpOnlyCookieOfRefreshToken(loginTokens.getRefreshToken());
        HttpHeaders headers = createHttpHeaders(refreshTokenCookie);

        return success(MEMBER_LOGIN_SUCCESS, accessTokenResponse, headers);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokens(@CookieValue(value = REFRESH_TOKEN) String refreshToken) {
        AuthTokens reissuedTokens = authService.reissueTokens(refreshToken);

        AccessTokenResponse accessTokenResponse = AccessTokenResponse.of(reissuedTokens.getAccessToken());
        ResponseCookie refreshTokenCookie = createHttpOnlyCookieOfRefreshToken(reissuedTokens.getRefreshToken());
        HttpHeaders headers = createHttpHeaders(refreshTokenCookie);

        return success(TOKEN_REFRESH_SUCCESS, accessTokenResponse, headers);
    }

    private ResponseCookie createHttpOnlyCookieOfRefreshToken(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                //.secure(true)
                .path(REFRESH_TOKEN_PATH)
                .maxAge(REFRESH_TOKEN_MAX_AGE)
                .sameSite(LAX)
                .build();
    }

    private HttpHeaders createHttpHeaders(ResponseCookie refreshTokenCookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return headers;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = AUTHORIZATION_HEADER) String authorizationHeader) {
        String accessToken = extractAccessTokenOrThrow(authorizationHeader);
        authService.logout(accessToken);
        return success(MEMBER_LOGOUT_SUCCESS);
    }

    private String extractAccessTokenOrThrow(String header) {
        if (header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_BEGIN_INDEX);
        } else {
            throw new BusinessException(INVALID_ACCESS_TOKEN);
        }
    }

}
