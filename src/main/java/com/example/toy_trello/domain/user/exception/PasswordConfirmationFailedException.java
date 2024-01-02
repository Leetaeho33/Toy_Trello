package com.example.toy_trello.domain.user.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class PasswordConfirmationFailedException extends RestApiException {

    public PasswordConfirmationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
