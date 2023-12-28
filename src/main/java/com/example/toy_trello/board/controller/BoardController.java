package com.example.toy_trello.board.controller;

import com.example.toy_trello.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.board.dto.BoardResponseDto;
import com.example.toy_trello.board.entity.Board;
import com.example.toy_trello.board.service.BoardService;
import com.example.toy_trello.global.dto.CommonResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping
  public ResponseEntity<CommonResponseDto> createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {

    boardService.createBoard(boardCreateRequestDto);
    return ResponseEntity.ok().body(new CommonResponseDto("생성 되었습니다.", HttpStatus.OK.value()));
  }

//  @GetMapping
//  public List<Board> getBoardList() {
//    return boardService.getBoardList();
//  }

  @GetMapping
  public Page<BoardResponseDto> getBoardList(Pageable pageable) {

      return boardService.getBoardList(pageable);

  }

  @GetMapping("/{boardId}")
  public Board getBoard(@PathVariable Long boardId) {
    return boardService.getBoard(boardId);

  }

  @PatchMapping("/{boardId}")
  public ResponseEntity<CommonResponseDto> updateBoard(@PathVariable Long boardId,
                                                       @RequestBody BoardCreateRequestDto boardCreateRequestDto){
      boardService.updateBoard(boardId, boardCreateRequestDto);
    return ResponseEntity.ok().body(new CommonResponseDto("수정 되었습니다.", HttpStatus.OK.value()));
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<CommonResponseDto> deleteBoard(@PathVariable Long boardId) {

      boardService.deleteBoard(boardId);
    return ResponseEntity.ok().body(new CommonResponseDto("삭제 되었습니다.", HttpStatus.OK.value()));

  }
}
