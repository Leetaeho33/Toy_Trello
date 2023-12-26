package com.example.toy_trello.board.service;

import com.example.toy_trello.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.board.dto.BoardResponseDto;
import com.example.toy_trello.board.entity.Board;
import com.example.toy_trello.board.repository.BoardRepository;
import java.util.ArrayList;
import java.util.List;
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

  public List<BoardResponseDto> getBoardList() {
    List<Board> boardList = boardRepository.findAll();
    List<BoardResponseDto> boardResponseDto = new ArrayList<>();

    for (Board board : boardList) {
      boardResponseDto.add(new BoardResponseDto(board));
    }

    return boardResponseDto;

  }

  public Board getBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new IllegalArgumentException("게시글이 업습니다."));
    return board;
  }
}
