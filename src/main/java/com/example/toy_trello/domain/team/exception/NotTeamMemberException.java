package com.example.toy_trello.domain.team.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class NotTeamMemberException extends RestApiException {
    public NotTeamMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
