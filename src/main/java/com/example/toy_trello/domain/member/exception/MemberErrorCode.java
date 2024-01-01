package com.example.toy_trello.domain.member.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 멤버입니다."),
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND,"멤버가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

