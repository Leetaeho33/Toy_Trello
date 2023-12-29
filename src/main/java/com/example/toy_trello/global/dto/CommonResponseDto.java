package com.example.toy_trello.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer statusCode;
}