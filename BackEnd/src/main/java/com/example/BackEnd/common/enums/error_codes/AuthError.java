package com.example.BackEnd.common.enums.error_codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthError implements Errors {

    // 400
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST.value(), "이메일 인증이 필요합니다."),

    // 401
    EMAIL_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED.value(), "입력하신 인증 번호가 일치하지 않습니다."),
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "아이디 또는 비밀번호가 올바르지 않습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "사용자 인증에 실패하였습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "사용자 인증에 실패하였습니다."),
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "사용자 인증에 실패하였습니다.");

    private final int code;
    private final String message;

}
