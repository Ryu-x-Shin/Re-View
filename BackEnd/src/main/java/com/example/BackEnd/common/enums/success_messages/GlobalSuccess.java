package com.example.BackEnd.common.enums.success_messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalSuccess implements Success {

    EMAIL_SEND_SUCCESS(HttpStatus.OK.value(), "이메일을 성공적으로 전송하였습니다.");

    private final int code;
    private final String message;

}
