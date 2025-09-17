package com.example.BackEnd.common.enums.error_codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberError implements Errors {

    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN.value(), "이메일 인증이 필요합니다."),

    EMAIL_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST.value(), "입력하신 인증 번호가 일치하지 않습니다."),

    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "이미 해당 이메일로 가입한 계정이 존재합니다. 새로운 이메일 주소를 다시 입력해 주십시오."),
    USERNAME_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "이미 해당 ID가 존재합니다. 새로운 ID를 다시 입력해 주십시오."),
    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "이미 해당 닉네임이 존재합니다. 새로운 닉네임을 다시 입력해 주십시오.");

    private final int code;
    private final String message;

}
