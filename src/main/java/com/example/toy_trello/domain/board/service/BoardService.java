package com.example.toy_trello.domain.board.service;

import static com.example.toy_trello.domain.board.exception.BoardErrorCode.NO_ALREADY_BOARD;
import static com.example.toy_trello.domain.board.exception.BoardErrorCode.NO_BOARD;

import com.example.toy_trello.domain.board.dto.BoardCreateRequestDto;
import com.example.toy_trello.domain.board.dto.BoardResponseDto;
import com.example.toy_trello.domain.board.dto.BoardUpdateRequestDto;
import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.board.exception.BoardExistsException;
import com.example.toy_trello.domain.board.repository.BoardRepository;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import com.example.toy_trello.global.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  public void createBoard(BoardCreateRequestDto boardCreateRequestDto,
                          UserDetailsImpl userDetailsImpl) {

    Board board = new Board(boardCreateRequestDto, userDetailsImpl);

    boardRepository.save(board);
  }

    public List<BoardResponseDto> getBoardList() {
    List<Board> boardList = boardRepository.findAll();
    List<BoardResponseDto> boardResponseDto = new ArrayList<>();

    for(Board board : boardList) {
      boardResponseDto.add(new BoardResponseDto(board));
    }
    return boardResponseDto;
  }

//  public Page<BoardResponseDto> getBoardList(Pageable pageable) {
//    // defalult값 10개씩 보여주기
//    pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
//
//    Page<Board> boardPage = boardRepository.findAll(pageable);
//
//    return boardPage.map(board ->
//        new BoardResponseDto(board.getBoardId(),
//            board.getBoardName(),
//            board.getDescription(),
//            board.getBackgroundColor()));
//
//  }

  public BoardResponseDto getBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new BoardExistsException(NO_BOARD));  // 보드가 존재하지 않습니다.

    BoardResponseDto boardResponseDto = new BoardResponseDto(board);  // 이거 안해놓으면 modified가 포스트맨에서 제일 위로 올라오던데 혹시 다른 방법이 있을까요?

    return boardResponseDto;
  }

  public void updateBoard(Long boardId,
      BoardUpdateRequestDto boardUpdateRequestDto,
      UserDetailsImpl userDetailsImpl) {

    Board board = idAndUsername(boardId, userDetailsImpl);

    board.update(boardUpdateRequestDto);

    boardRepository.save(board);
  }

  @Transactional
  public void deleteBoard(Long boardId, UserDetailsImpl userDetailsImpl) {
    Board board = idAndUsername(boardId, userDetailsImpl);
    boardRepository.delete(board);
  }

  // 보드에 사용자 추가하는 메서드
  @Transactional
  public void addUserToBoard(Long boardId, Long userId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new BoardExistsException(NO_BOARD));  // 보드가 존재하지 않습니다.
    User user = userRepository.findById(userId).orElseThrow(() ->
        new IllegalArgumentException("사용자가 존재하지 않습니다."));

    if (!board.getUsers().contains(user)) {
      board.getUsers().add(user);    // 해당 컬렉션에 user객체를 추가하는 작업 , 이렇게 하면 사용자를 해당 게시판에 추가 할 수 있다.
                                     // 현재는 메서드를 바로 부르는 형태로 사용
                                     // 그러나 객체를 최대한 활용하기 위해 사용한다면
                                     // addBoardList() 와 addUserList() 메서드를 만들어서 사용

      boardRepository.save(board);
    } else {
      throw new BoardExistsException(NO_ALREADY_BOARD);  // 해당 사용자는 이미 보드에 추가되어 있습니다.
    }
  }

  private Board idAndUsername(Long boardId, UserDetailsImpl userDetailsImpl) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new BoardExistsException(NO_BOARD)); // 보드가 존재하지 않습니다.

    if (!board.getUsername().equals(userDetailsImpl.getUsername())) {
      throw new IllegalArgumentException("작성자만 게시글을 수정 및 삭제 할 수 있습니다.");
    }
    return board;
  }
}
