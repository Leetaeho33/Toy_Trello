package com.example.toy_trello.column.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class ColumnNotFoundException extends RestApiException {
    public ColumnNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
