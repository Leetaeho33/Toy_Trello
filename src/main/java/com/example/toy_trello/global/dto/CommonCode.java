package com.example.toy_trello.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonCode {

    OK("성공", HttpStatus.OK.value());

    private final String message;
    private final Integer code;

    CommonCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
