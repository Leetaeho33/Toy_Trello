package com.example.toy_trello.column.dto.requestDto;

import lombok.Getter;

@Getter
public class ColumnRequestDto {
    private String name;


    public void setName(String name) {
        this.name = name;
    }
}
