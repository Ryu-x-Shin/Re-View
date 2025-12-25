package com.example.BackEnd.common.enums.error_codes;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum WorkErrorCode implements Errors {

    // 404
    WORK_NOT_FOUND(NOT_FOUND.value(), "해당 작품 정보가 존재하지 않습니다.");

    private final int code;
    private final String message;

}
