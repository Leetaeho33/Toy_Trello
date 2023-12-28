package com.example.toy_trello.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardCreateRequestDto {
  private String cardName;
  private String cardDescription;
  private String cardColor;
 // private UserState userState;

}
