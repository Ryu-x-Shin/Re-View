package com.example.BackEnd.common.enums.success_messages;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements Success {

    // 200
    USERNAME_AVAILABLE(OK.value(), "사용 가능한 아이디입니다."),
    NICKNAME_AVAILABLE(OK.value(), "사용 가능한 닉네임입니다.");

    private final int code;
    private final String message;

}
