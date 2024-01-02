package com.example.toy_trello.domain.card.dto;

import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.member.entity.MemberRole;
import lombok.Getter;


@Getter
public class CardResponseDto {
  private final Long cardId;
  private final String cardName;
  private final String cardDescription;
  private final String cardColor;

  private final String dueDate;
  private final String username;
  private final Long cardOrder;
  private final Long teamId;


  public CardResponseDto(Card card){
    this.cardId = card.getCardId();
    this.cardName = card.getCardName();
    this.cardDescription = card.getCardDescription();
    this.cardColor = card.getCardColor();

    this.dueDate = String.valueOf(card.getDueDate());
    this.username = card.getUser().getUsername();
    this.cardOrder = card.getCardOrder();
    this.teamId = card.getTeam().getId();


  }

  public static CardResponseDto of(Card card) {
    return new CardResponseDto(card);
  }
}
