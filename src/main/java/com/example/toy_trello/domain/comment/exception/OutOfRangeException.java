package com.example.toy_trello.domain.comment.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class OutOfRangeException extends RestApiException {
    public OutOfRangeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
