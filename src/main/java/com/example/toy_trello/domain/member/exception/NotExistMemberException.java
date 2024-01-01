package com.example.toy_trello.domain.member.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class NotExistMemberException extends RestApiException {
    public NotExistMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
