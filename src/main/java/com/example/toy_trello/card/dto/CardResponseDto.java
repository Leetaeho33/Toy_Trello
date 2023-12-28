package com.example.toy_trello.card.dto;

import com.example.toy_trello.card.entity.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
public class CardResponseDto {
  private final Long cardId;
  private final String cardName;
  private final String cardDescription;
  private final String cardColor;
 // private UserState userstate;

  public CardResponseDto(Card card){
    this.cardId = card.getCardId();
    this.cardName = card.getCardName();
    this.cardDescription = card.getCardDescription();
    this.cardColor = card.getCardColor();
    //this.userstate = card.getUserstate();
  }

  public static CardResponseDto of(Card card) {
    return new CardResponseDto(card);
  }
}
