package com.example.toy_trello.domain.team.exception;

import com.example.toy_trello.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TeamErrorCode implements ErrorCode {
    ONLY_AUTHORIZED_LEADER(HttpStatus.FORBIDDEN, "팀장만 접근 가능한 권한입니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 팀입니다."),
    DUPLICATED_TEAM(HttpStatus.BAD_REQUEST,"중복된 팀 이름입니다."),
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "한팀에는 유저가 한명만 들어갈 수 있습니다."),
    DUPLICATED_LEADER(HttpStatus.BAD_REQUEST, "한팀에는 팀장이 한명이어야 합니다."),
    NOT_TEAM_MEMBER(HttpStatus.BAD_REQUEST,"현재 로그인 된 유저는 이 팀의 멤버가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}