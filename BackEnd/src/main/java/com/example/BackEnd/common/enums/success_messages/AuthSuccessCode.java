package com.example.BackEnd.common.enums.success_messages;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements Success {

    // 200
    EMAIL_VERIFICATION_SUCCESS(OK.value(), "이메일 인증에 성공하였습니다."),
    TOKEN_REFRESH_SUCCESS(OK.value(), "인증에 성공하였습니다."),
    MEMBER_LOGIN_SUCCESS(OK.value(), "로그인에 성공하였습니다."),

    // 201
    MEMBER_SIGNUP_SUCCESS(CREATED.value(), "성공적으로 회원 가입되었습니다."),

    // 204
    MEMBER_LOGOUT_SUCCESS(NO_CONTENT.value(), "로그아웃 되었습니다.");

    private final int code;
    private final String message;

}
