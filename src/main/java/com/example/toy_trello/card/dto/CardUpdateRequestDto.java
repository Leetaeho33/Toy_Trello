package com.example.toy_trello.card.dto;

public record CardUpdateRequestDto(
    String cardName,
    String cardDescription,
    String cardColor
) {

}
