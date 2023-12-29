package com.example.toy_trello.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardCreateRequestDto {
  private String boardName;
  private String description;
  private String backgroundColor;
}
