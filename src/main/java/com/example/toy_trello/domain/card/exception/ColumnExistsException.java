package com.example.toy_trello.domain.card.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class ColumnExistsException extends RestApiException {

  public ColumnExistsException(ErrorCode errorCode){
    super(errorCode);
  }

}
