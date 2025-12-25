package com.example.BackEnd.common.enums.success_messages;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum CommonSuccessCode implements Success {

    // 200
    EMAIL_SEND_SUCCESS(OK.value(), "이메일을 성공적으로 전송하였습니다.");

    private final int code;
    private final String message;

}
