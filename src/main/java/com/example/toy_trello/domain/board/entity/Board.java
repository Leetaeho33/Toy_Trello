package com.example.toy_trello.domain.board.entity;

import com.example.toy_trello.domain.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.domain.board.dto.BoardUpdateRequestDto;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.util.BaseEntity;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

  @Column
  private String username;  // User에서 빼와야되는데...흠

  @ManyToMany
  @JoinTable(
      name = "board_user",
      joinColumns = @JoinColumn(name = "board_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new HashSet<>();


  public Board(BoardCreateRequestDto boardCreateRequestDto, UserDetailsImpl userDetailsImpl) {
    this.boardName = boardCreateRequestDto.getBoardName();
    this.backgroundColor = boardCreateRequestDto.getBackgroundColor();
    this.description = boardCreateRequestDto.getDescription();
    this.username = userDetailsImpl.getUsername();  // set타입으로 변환은 불가능하다는데..요?
  }

  public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
    this.boardName = boardUpdateRequestDto.getBoardName();
    this.backgroundColor = boardUpdateRequestDto.getBackgroundColor();
    this.description = boardUpdateRequestDto.getDescription();
  }
}
