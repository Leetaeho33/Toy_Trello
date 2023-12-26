package com.example.toy_trello.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardId;

  @Column
  private String boardName;   // 보드 이름

  @Column
  private String backgroundColor;  // 배경 색상

  @Column
  private String description; // 설명

  public Board(String boardName, String backgroundColor, String description) {
    this.boardName = boardName;
    this.backgroundColor = backgroundColor;
    this.description = description;
  }
}
