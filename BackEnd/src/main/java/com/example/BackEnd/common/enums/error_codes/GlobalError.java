package com.example.BackEnd.common.enums.error_codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalError implements Errors {

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "입력하신 값이 정확한지 다시 한번 확인해주십시오."),

    MEDIATYPE_NOT_SUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "입력하신 값이 정확한지 다시 한번 확인해주십시오."),

    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "메일 전송 중 알 수 없는 오류가 발생하였습니다."),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다."),
    JSON_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다."),
    KAFKA_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류가 발생하였습니다.");

    private final int code;
    private final String message;

}
