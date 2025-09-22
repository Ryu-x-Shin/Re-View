package com.example.BackEnd.common.enums.success_messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccess implements Success {

    // 200
    MEMBER_LOGIN_SUCCESS(HttpStatus.OK.value(), "로그인에 성공하였습니다."),

    // 201
    MEMBER_SIGNUP_SUCCESS(HttpStatus.CREATED.value(), "성공적으로 회원 가입되었습니다."),

    // 204
    MEMBER_LOGOUT_SUCCESS(HttpStatus.NO_CONTENT.value(), "로그아웃 되었습니다.");

    private final int code;
    private final String message;

}
