package com.example.toy_trello.domain.card.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode implements ErrorCode {

  COLUMN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 컬럼입니다."),
  CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카드입니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
  SAME_CARD(HttpStatus.BAD_REQUEST, "같은 카드입니다."),
  SAME_ID_MAX_CARD_ORDER(HttpStatus.BAD_REQUEST, "같은 id, 최대 cardOrder입니다.");

  private final HttpStatus httpStatus;
  private final String message;


}
