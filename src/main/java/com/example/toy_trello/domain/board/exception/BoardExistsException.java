package com.example.toy_trello.domain.board.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class BoardExistsException extends RestApiException {

  public BoardExistsException(ErrorCode errorCode) {
    super(errorCode);
  }
}
