package com.example.toy_trello.domain.comment.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import com.example.toy_trello.global.exception.RestApiException;

public class CommentExistsException extends RestApiException {

    public CommentExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
