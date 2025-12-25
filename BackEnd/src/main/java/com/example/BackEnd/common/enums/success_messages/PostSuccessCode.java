package com.example.BackEnd.common.enums.success_messages;

import lombok.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum PostSuccessCode implements Success {

    // 200
    POST_LIST_FETCH_SUCCESS(OK.value(), "게시글 목록 조회에 성공하였습니다."),
    POST_UPDATED(OK.value(), "게시글이 수정되었습니다."),
    POST_DELETED(OK.value(), "게시글이 삭제되었습니다."),

    // 201
    POST_CREATED(CREATED.value(), "게시글이 작성되었습니다.");

    private final int code;
    private final String message;

}
