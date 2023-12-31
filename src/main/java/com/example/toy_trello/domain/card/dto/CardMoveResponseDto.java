package com.example.toy_trello.domain.card.dto;

import com.example.toy_trello.column.entity.Column;
import lombok.Builder;

@Builder
public record CardMoveResponseDto(
    Long columnId,
    String columnName,
    Long cardId,
    Long cardOrder
) {

}
