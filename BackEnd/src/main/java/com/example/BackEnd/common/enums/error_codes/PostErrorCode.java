package com.example.BackEnd.common.enums.error_codes;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements Errors {

    // 400
    POST_BAD_REQUEST(BAD_REQUEST.value(), "요청값이 정확한지 다시 한번 확인해주십시오."),

    // 403
    WRITER_MISMATCH(FORBIDDEN.value(), "해당 게시글을 수정 또는 삭제할 수 없습니다."),

    // 404
    POST_NOT_FOUND(NOT_FOUND.value(), "존재하지 않는 게시글입니다.");

    private final int code;
    private final String message;

}
