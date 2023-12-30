package com.example.toy_trello.domain.team.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class TeamNotFoundException extends RestApiException {
    public TeamNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
