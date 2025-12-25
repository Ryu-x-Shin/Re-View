package com.example.BackEnd.common.enums.error_codes;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements Errors {

    // 409
    USERNAME_ALREADY_EXISTS(CONFLICT.value(), "해당 아이디가 이미 존재합니다.\n새로운 아이디를 다시 입력해 주십시오."),
    NICKNAME_ALREADY_EXISTS(CONFLICT.value(), "해당 닉네임이 이미 존재합니다.\n새로운 닉네임을 다시 입력해 주십시오."),
    EMAIL_ALREADY_EXISTS(CONFLICT.value(), "해당 이메일로 가입한 계정이 이미 존재합니다.\n새로운 이메일을 다시 입력해 주십시오.");

    private final int code;
    private final String message;

}
