package com.example.toy_trello.domain.user.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class IncorrectPasswordException extends RestApiException {

    public IncorrectPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
