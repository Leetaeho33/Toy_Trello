package com.example.toy_trello.domain.board.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
  @Test
  @DisplayName("보드 객체 생성")
  void createBoard() {
    // Given
    String boardName = "보드 이름";
    String backgroundColor = "보라색";
    String description = "설명";
    String username = "유저이름1";

    // When
    Board board = new Board();
    board.setBoardName(boardName);
    board.setBackgroundColor(backgroundColor);
    board.setDescription(description);
    board.setUsername(username);

    // Then
    assertThat(board.getBoardName()).isEqualTo(boardName);
    assertThat(board.getBackgroundColor()).isEqualTo(backgroundColor);
    assertThat(board.getDescription()).isEqualTo(description);
    assertThat(board.getUsername()).isEqualTo(username);

  }
}