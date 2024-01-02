package com.example.toy_trello.domain.card.dto;

public record ColumnWithCardsResponseDto(
    Long columnId,
    String columnName,
    PageDto cards
) {
}

