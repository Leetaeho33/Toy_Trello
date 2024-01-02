package com.example.toy_trello.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor    // TEST코드
@AllArgsConstructor   // TEST코드
public class BoardCreateRequestDto {
  private String boardName;
  private String description;
  private String backgroundColor;
}
