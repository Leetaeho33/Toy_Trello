package com.example.toy_trello.board.controller;

import com.example.toy_trello.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.board.dto.BoardResponseDto;
import com.example.toy_trello.board.service.BoardService;
import com.example.toy_trello.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<CommonResponseDto> createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {

    try {
      boardService.createBoard(boardCreateRequestDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
    return ResponseEntity.ok().body(new CommonResponseDto("생성 되었습니다.", HttpStatus.OK.value()));
  }
}
