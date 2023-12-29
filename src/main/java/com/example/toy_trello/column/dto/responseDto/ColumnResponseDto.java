package com.example.toy_trello.column.dto.responseDto;

public class ColumnResponseDto {
    private Long id;
    private String name;

    public ColumnResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
