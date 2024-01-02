package com.example.toy_trello.domain.board.service;

import com.example.toy_trello.domain.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.domain.board.dto.BoardResponseDto;
import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.board.repository.BoardRepository;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import com.example.toy_trello.global.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@DataJpaTest // @Mock 사용을 위해 설정 (가짜 객체를 사용하려고 만듬) @ExtendWith(SpringExtension.class) 포함
//class BoardServiceTest {
//
//  @Autowired
//  BoardService boardService;
//  @Mock
//  BoardRepository boardRepository;
//  @Mock
//  UserRepository userRepository;
//
//  User user;
//  @Test
//  @DisplayName("보드 작성하기")
//  void test1() {
//    // given
//    Long boardId = 1L;
//
//    BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(
//        "보드1",
//        "설명1",
//        "빨간색"
//    );
//
//    user = userRepository.findById(1L).orElse(null);
//    // when
//
//    BoardResponseDto boardResponseDto = boardService.createBoard(boardCreateRequestDto, );
//
//    // then
//
//  }
//}