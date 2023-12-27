package com.example.toy_trello.board.entity;

import com.example.toy_trello.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

//  @ManyToOne
//  private User userId;

//  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//  private List<Column> columnList;

  public Board(String boardName, String backgroundColor, String description) {
    this.boardName = boardName;
    this.backgroundColor = backgroundColor;
    this.description = description;
  }

  public void update(BoardCreateRequestDto boardCreateRequestDto) {
    this.boardName = boardCreateRequestDto.getBoardName();
    this.backgroundColor = boardCreateRequestDto.getBackgroundColor();
    this.description = boardCreateRequestDto.getDescription();
  }
}
