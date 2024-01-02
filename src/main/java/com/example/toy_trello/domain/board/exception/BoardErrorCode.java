package com.example.toy_trello.domain.board.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {
  NO_BOARD(HttpStatus.NOT_FOUND, "보드가 존재하지 않습니다."),
  NO_ALREADY_BOARD(HttpStatus.ALREADY_REPORTED, "해당 사용자는 이미 보드에 추가되어 있습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
