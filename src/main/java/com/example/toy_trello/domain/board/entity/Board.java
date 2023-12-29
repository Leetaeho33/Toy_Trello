package com.example.toy_trello.domain.board.entity;

import com.example.toy_trello.domain.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.domain.util.BaseEntity;
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
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardId;

  @Column
  private String boardName;   // 보드 이름

  @Column
  private String backgroundColor;  // 배경 색상

  @Column
  private String description; // 설명

//  @ManyToMany
//  private User userId;

//  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//  private List<Column> columnList;

  public Board(BoardCreateRequestDto boardCreateRequestDto) {
    this.boardName = boardCreateRequestDto.getBoardName();
    this.backgroundColor = boardCreateRequestDto.getBackgroundColor();
    this.description = boardCreateRequestDto.getDescription();
  }

  public void update(BoardCreateRequestDto boardCreateRequestDto) {
    this.boardName = boardCreateRequestDto.getBoardName();
    this.backgroundColor = boardCreateRequestDto.getBackgroundColor();
    this.description = boardCreateRequestDto.getDescription();
  }
}
