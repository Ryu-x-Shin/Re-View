package com.example.BackEnd.common.enums.error_codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements Errors {

    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "입력하신 값이 정확한지 다시 한번 확인해주십시오."),

    // 403
    ACCESS_DENIED(FORBIDDEN.value(), "해당 리소스에 접근할 수 없습니다."),

    // 415
    MEDIATYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE.value(), "입력하신 값이 정확한지 다시 한번 확인해주십시오."),

    // 500
    EMAIL_SEND_FAILED(INTERNAL_SERVER_ERROR.value(), "메일 전송 중 알 수 없는 오류가 발생하였습니다."),
    DATA_ACCESS_ERROR(INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다."),
    JSON_PROCESSING_FAILED(INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다."),
    REDIS_CONNECTION_ERROR(INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다."),
    KAFKA_CONNECTION_ERROR(INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다.");

    private final int code;
    private final String message;

}
