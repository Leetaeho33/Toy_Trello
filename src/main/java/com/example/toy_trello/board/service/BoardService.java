package com.example.toy_trello.board.service;

import com.example.toy_trello.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.board.entity.Board;
import com.example.toy_trello.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  public void createBoard(BoardCreateRequestDto boardCreateRequestDto) {
    String boardName = boardCreateRequestDto.getBoardName();
    String backgroundColor = boardCreateRequestDto.getBackgroundColor();
    String description = boardCreateRequestDto.getDescription();

    Board board = new Board(boardName, backgroundColor, description);

    boardRepository.save(board);

  }
}
