package com.example.toy_trello.domain.user.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    REQUIRED_USERNAME(HttpStatus.BAD_REQUEST, "username을 입력하세요."),
    REQUIRED_PASSWORD(HttpStatus.BAD_REQUEST, "password를 입력하세요."),
    REQUIRED_EMAIL(HttpStatus.BAD_REQUEST, "email을 입력하세요."),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 사용자명 입니다."),
    NON_USER_EXIST(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."),
    SELF_AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "본인만 정보 수정 및 탈퇴 가능합니다."),
    INCORRECT_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    PASSWORD_CONFIRMATION_FAILED(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
