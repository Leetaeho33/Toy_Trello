package com.example.toy_trello.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponseDto {
    private String msg;
    private int statusCode;
}