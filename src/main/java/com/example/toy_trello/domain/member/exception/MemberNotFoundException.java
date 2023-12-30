package com.example.toy_trello.domain.member.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class MemberNotFoundException extends RestApiException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
