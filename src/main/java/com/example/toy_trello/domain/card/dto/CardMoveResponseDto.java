package com.example.toy_trello.domain.card.dto;

import lombok.Builder;

@Builder
public record CardMoveResponseDto(
    Long columnId,
    String columnName,
    Long cardId,
    Long cardOrder
) {

}
