package com.example.BackEnd.common.enums.success_messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccess implements Success {

    // 200
    USERNAME_CHECK_SUCCESS(HttpStatus.OK.value(), "사용 가능한 ID입니다."),
    NICKNAME_CHECK_SUCCESS(HttpStatus.OK.value(), "사용 가능한 닉네임입니다."),
    EMAIL_VERIFICATION_SUCCESS(HttpStatus.OK.value(), "이메일 인증에 성공하였습니다."),
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK.value(), "인증에 성공하였습니다.");

    private final int code;
    private final String message;

}
