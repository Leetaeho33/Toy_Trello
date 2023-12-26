package com.example.toy_trello.board.dto;

import com.example.toy_trello.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String boardName;
    private String backgroundColor;
    private String description;

    public BoardResponseDto(Board board) {
        this.id = board.getBoardId();
        this.boardName = board.getBoardName();
        this.backgroundColor = board.getBackgroundColor();
        this.description = board.getDescription();
    }
}
