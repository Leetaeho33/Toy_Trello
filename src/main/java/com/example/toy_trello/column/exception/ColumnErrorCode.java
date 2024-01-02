package com.example.toy_trello.column.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ColumnErrorCode implements ErrorCode {
    COLUMN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 컬럼입니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 보드입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}