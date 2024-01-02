package com.example.toy_trello.domain.board.controller;

import com.example.toy_trello.domain.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.domain.board.dto.BoardPageDto;
import com.example.toy_trello.domain.board.dto.BoardResponseDto;
import com.example.toy_trello.domain.board.dto.BoardUpdateRequestDto;
import com.example.toy_trello.domain.board.service.BoardService;
import com.example.toy_trello.global.dto.CommonResponseDto;
import com.example.toy_trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping

  public ResponseEntity<CommonResponseDto> createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

    boardService.createBoard(boardCreateRequestDto, userDetailsImpl);
    return ResponseEntity.ok().body(new CommonResponseDto("생성 되었습니다.", HttpStatus.OK.value()));
  }


   //전체 조회 (페이징)
  @GetMapping
  public BoardPageDto getBoardList() {

      return boardService.getBoardList();

  }

  // 선택 조회
  @GetMapping("/{boardId}")
  public BoardResponseDto getBoard(@PathVariable Long boardId) {

    return boardService.getBoard(boardId);

  }

  @PatchMapping("/{boardId}")
  public ResponseEntity<CommonResponseDto> updateBoard(@PathVariable Long boardId,
                                                       @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
      boardService.updateBoard(boardId, boardUpdateRequestDto, userDetailsImpl);

    return ResponseEntity.ok().body(new CommonResponseDto("수정 되었습니다.", HttpStatus.OK.value()));
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<CommonResponseDto> deleteBoard(@PathVariable Long boardId,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

      boardService.deleteBoard(boardId, userDetailsImpl);
    return ResponseEntity.ok().body(new CommonResponseDto("삭제 되었습니다.", HttpStatus.OK.value()));

  }

  // 보드에 사용자 추가하는 메서드
  @PostMapping("/{boardId}/users/{userId}")
  public ResponseEntity<CommonResponseDto> addUserToBoard(@PathVariable Long boardId,
                                                          @PathVariable Long userId) {
    boardService.addUserToBoard(boardId, userId);
    return ResponseEntity.ok().body(new CommonResponseDto("보드에 추가되었습니다.", HttpStatus.OK.value()));
  }

}
