package com.example.toy_trello.domain.card.dto;

public record CardUpdateRequestDto(
    String cardName,
    String cardDescription,
    String cardColor
) {

}
