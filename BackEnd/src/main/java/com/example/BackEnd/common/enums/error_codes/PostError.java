package com.example.BackEnd.common.enums.error_codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostError implements Errors {

    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "요청값이 정확한지 다시 한번 확인해주십시오.");

    private final int code;
    private final String message;

}
