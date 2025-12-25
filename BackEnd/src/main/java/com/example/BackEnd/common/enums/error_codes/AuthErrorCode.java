package com.example.BackEnd.common.enums.error_codes;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements Errors {

    // 400
    EMAIL_NOT_VERIFIED(BAD_REQUEST.value(), "이메일 인증이 필요합니다."),
    SIGNUP_OTP_MISMATCH(BAD_REQUEST.value(), "입력하신 인증번호가 일치하지 않습니다."),

    // 401
    AUTH_INVALID_CREDENTIALS(UNAUTHORIZED.value(), "아이디 또는 비밀번호가 올바르지 않습니다."),
    INVALID_ACCESS_TOKEN(UNAUTHORIZED.value(), "사용자 인증에 실패하였습니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED.value(), "사용자 인증에 실패하였습니다."),
    MEMBER_NOT_FOUND(UNAUTHORIZED.value(), "사용자 인증에 실패하였습니다.");

    private final int code;
    private final String message;

}
