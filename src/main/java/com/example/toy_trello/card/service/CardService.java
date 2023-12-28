package com.example.toy_trello.card.service;

import com.example.toy_trello.card.dto.CardCreateRequestDto;
import com.example.toy_trello.card.dto.CardResponseDto;
import com.example.toy_trello.card.dto.CardTransferResponseDto;
import com.example.toy_trello.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.card.dto.PageDto;
import com.example.toy_trello.card.entity.Card;
import com.example.toy_trello.card.repository.CardRepository;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import com.example.toy_trello.global.dto.CommonResponseDto;
import com.example.toy_trello.global.security.UserDetailsImpl;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "CardService")
@RequiredArgsConstructor
@Service
public class CardService {

  private final CardRepository cardRepository;
  private final UserRepository userRepository;
  public CardResponseDto createCard(CardCreateRequestDto requestDto) throws ParseException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    Card card = Card.builder()
        .cardName(requestDto.getCardName())
        .cardDescription(requestDto.getCardDescription())
        .user(userDetails.getUser())
        .dueDate(requestDto.getDueDate())
        .cardColor(requestDto.getCardColor())
        .build();
    Card cardSaved = cardRepository.save(card);
    return new CardResponseDto(cardSaved);
  }

//  public PageDto getCards(Pageable pageable) {
//    Page<Card> result = cardRepository.findByColumnId(columnId, pageable); // commentId를 컨트롤러에서 받아서 repository에서 찾아야된다.
//    var data = result.getContent().stream()
//        .map(CardResponseDto::of)
//        .toList();
//
//    return new PageDto(data,
//        result.getTotalElements(),
//        result.getTotalPages(),
//        pageable.getPageNumber(),
//        data.size()
//    );
//  }

  public ResponseEntity<?> updateWorkerTransferCard(Long cardId,Long userId){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    User user = userRepository.findById(userDetails.getUser().getUserId())
        .orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다."));//선택한 유저 찾기

    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));//선택한 카드 찾기

    if (!card.getUser().getUserId().equals(user.getUserId())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 카드를 수정할 수 없습니다.");
      //카드에 저장된 아이디와 현재 저장된 유저와 id 일치 여부 확인
    }

    User userUpdated = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다."));

    card.userUpdate(userUpdated);
    cardRepository.save(card);

    return ResponseEntity.ok().body("작업자가 변경되었습니다!");
  }

  public ResponseEntity<?> updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    User user = userRepository.findById(userDetails.getUser().getUserId())
        .orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다."));//선택한 유저 찾기


    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));//선택한 카드 찾기

    if (!card.getUser().getUserId().equals(user.getUserId())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 게시글을 수정할 수 없습니다.");
      //카드에 저장된 아이디와 현재 저장된 유저와 id 일치 여부 확인
    }

    card.update(cardUpdateRequestDto);
    cardRepository.save(card);
    return ResponseEntity.ok().body("업데이트를 성공하였습니다.");
  }

  public ResponseEntity<?> deleteCard(Long cardId) {
    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));
    cardRepository.delete(card);
    return ResponseEntity.ok().body("카드를 삭제 합니다.");
  }

  public CardResponseDto getCard(Long cardId) {
    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));
    return new CardResponseDto(card);
  }

//  public ResponseEntity<CardTransferResponseDto> cardTransferResponseDto(Long cardId, Long columnId, String cardOrder){
//    ColumnEntity columnEntity = columnRepository.findById(columnId).orElseThrow(() -> new IllegalArgumentException("선택한 컬럼은 존재하지 않습니다."));
//    Card card = cardRepository.findById(cardId).orElseThrow(()->new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));
//    List<ColumnEntity> result = commentRepository.findByPostId(postId, pageable);
//  }



}
