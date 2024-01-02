package com.example.toy_trello.board.service;

import com.example.toy_trello.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.board.dto.BoardResponseDto;
import com.example.toy_trello.board.entity.Board;
import com.example.toy_trello.board.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  public void createBoard(BoardCreateRequestDto boardCreateRequestDto) {
    Board board = new Board(boardCreateRequestDto);

    boardRepository.save(board);
  }

//  public List<Board> getBoardList() {
//
//    return boardRepository.findAll();
//  }
public Page<BoardResponseDto> getBoardList(Pageable pageable) {
  Page<Board> boardPage = boardRepository.findAll(pageable);

  return boardPage.map(board ->
      new BoardResponseDto(board.getBoardId(),
                           board.getBoardName(),
                           board.getDescription(),
                           board.getBackgroundColor()));

}

//  public Page<BoardResponseDto> getBoardList(int page,
//                                             int size,
//                                             String sortBy,
//                                             boolean isAsc) {
//
//    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//    Sort sort = Sort.by(direction, sortBy);
//    Pageable pageable = PageRequest.of(page, size, sort);
//
//    // 유저 가져오기 (권한 확인하기)
//
//    Page<Board> boardList;
//
//    boardList = boardRepository.findAll(pageable);
//
//    for (Board board : boardList) {
//      boardResponseDto.add(new BoardResponseDto(board));
//    }
//
//    return boardList.map(BoardResponseDto::new);
//  }

  public Board getBoard(Long boardId) {
    Board board = findById(boardId);
    return board;
  }

  public void updateBoard(Long boardId, BoardCreateRequestDto boardCreateRequestDto) {
    Board board = findById(boardId);

    board.update(boardCreateRequestDto);

    boardRepository.save(board);
  }


  public void deleteBoard(Long boardId) {
    Board board = findById(boardId);
     boardRepository.delete(board);
  }

  private Board findById(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(()->
        new IllegalArgumentException("게시글이 존재하지 않습니다."));
    // 수정이나 삭제할 때 예외처리 메세지를 다르게 하고 싶을땐 어떻게 해야할까..
    return board;
  }


//// 보드에 Column을 구현하기 위한 관련 코드
//  public BoardResponseDto getBoard(Long boardId) {
//    Board board = findById(boardId);
//    return new BoardResponseDto(board);
//  }
//
//  public void updateBoard(Long boardId, BoardCreateRequestDto boardCreateRequestDto, List<Long> columnOrder) {
//    Board board = findById(boardId);
//
//    board.update(boardCreateRequestDto);
//    boardRepository.save(board);
//
//    // Update column orders
//    for (int i = 0; i < columnOrder.size(); i++) {
//      Long columnId = columnOrder.get(i);
//      Column column = columnRepository.findById(columnId).orElse(null);
//      if (column != null) {
//        column.setOrder((long) i);
//        columnRepository.save(column);
//      }
//    }
//  }








}
