package com.example.toy_trello.domain.comment.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    OUT_OF_RANGE(HttpStatus.NOT_FOUND,"페이지 범위를 넘겼습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
