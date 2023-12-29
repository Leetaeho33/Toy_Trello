package com.example.toy_trello.domain.board.dto;

import com.example.toy_trello.domain.board.entity.Board;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String boardName;
    private String backgroundColor;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getBoardId();
        this.boardName = board.getBoardName();
        this.backgroundColor = board.getBackgroundColor();
        this.description = board.getDescription();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

  public BoardResponseDto(Long boardId, String boardName, String description, String backgroundColor) {
        this.id = boardId;
        this.boardName = boardName;
        this.backgroundColor = backgroundColor;
        this.description = description;
  }
}
