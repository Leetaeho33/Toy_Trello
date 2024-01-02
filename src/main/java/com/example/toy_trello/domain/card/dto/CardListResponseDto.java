package com.example.toy_trello.domain.card.dto;

public record CardListResponseDto(
    Long cardId,
    String cardName,
    Long cardOrder
) {

}
