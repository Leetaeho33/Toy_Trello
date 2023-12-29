package com.example.toy_trello.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
  private String boardName;
  private String description;
  private String backgroundColor;
}
