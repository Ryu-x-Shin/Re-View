package com.example.BackEnd.common.enums.success_messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostSuccess implements Success {

    // 200
    POST_LIST_FETCH_SUCCESS(HttpStatus.OK.value(), "게시글 목록 조회에 성공하였습니다.");

    private final int code;
    private final String message;

}
