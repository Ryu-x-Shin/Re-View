package com.example.BackEnd.common.enums.success_messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccess implements Success {

    USERNAME_CHECK_SUCCESS(HttpStatus.OK.value(), "사용 가능한 ID입니다."),
    NICKNAME_CHECK_SUCCESS(HttpStatus.OK.value(), "사용 가능한 닉네임입니다."),
    EMAIL_SEND_SUCCESS(HttpStatus.OK.value(), "이메일을 성공적으로 전송하였습니다."),
    EMAIL_VERIFICATION_SUCCESS(HttpStatus.OK.value(), "이메일 인증에 성공하였습니다."),

    MEMBER_SIGNUP_SUCCESS(HttpStatus.CREATED.value(), "성공적으로 회원 가입되었습니다.");

    private final int code;
    private final String message;

}
