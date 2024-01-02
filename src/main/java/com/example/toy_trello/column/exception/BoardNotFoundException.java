package com.example.toy_trello.column.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class BoardNotFoundException extends RestApiException {


    public BoardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}