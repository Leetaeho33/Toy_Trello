package com.example.toy_trello.column.dto.responseDto;

import lombok.Getter;

@Getter
public class ColumnResponseDto {
    private Long id;
    private String name;

    public ColumnResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
