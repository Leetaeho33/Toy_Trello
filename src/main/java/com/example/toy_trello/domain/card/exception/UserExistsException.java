package com.example.toy_trello.domain.card.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class UserExistsException extends RestApiException {
  public UserExistsException(ErrorCode errorCode){
    super(errorCode);
  }
}
