package com.example.toy_trello.domain.card.service;

import com.example.toy_trello.column.entity.Column;
import com.example.toy_trello.column.repository.ColumnRepository;
import com.example.toy_trello.domain.card.dto.CardCreateRequestDto;
import com.example.toy_trello.domain.card.dto.CardResponseDto;
import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.card.dto.PageDto;
import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.card.repository.CardRepository;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import com.example.toy_trello.global.security.UserDetailsImpl;
import java.text.ParseException;
import java.util.List;
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
public class CardService implements CardServiceInterface {

  private final CardRepository cardRepository;

  private final UserRepository userRepository;

  private final ColumnRepository columnRepository;

  @Override
  public UserDetailsImpl getAuth() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (UserDetailsImpl) authentication.getPrincipal();
  }

  public void insertCard(Long columnId, Long cardOrder) {//카드 끼워넣기
    List<Card> existingCards = cardRepository.findByColumn_IdAndCardOrderGreaterThanEqual(columnId,
        cardOrder);
    //컬럼안 카드 리스트 찾기
    for (Card cardOne : existingCards) {
      cardOne.setCardOrder(cardOne.getCardOrder() + 1);
      cardRepository.save(cardOne);
    }
  }

  public CardResponseDto createCard(CardCreateRequestDto requestDto) throws ParseException {
    Column column = columnRepository.findById(requestDto.getColumnId())
        .orElseThrow(() -> new IllegalArgumentException("컬럼이 존재하지 않습니다."));

    insertCard(requestDto.getColumnId(), requestDto.getCardOrder());

    Card card = Card.builder()
        .column(column)
        .cardName(requestDto.getCardName())
        .cardDescription(requestDto.getCardDescription())
        .user(getAuth().getUser())
        .dueDate(requestDto.getDueDate())
        .cardColor(requestDto.getCardColor())
        .cardOrder(requestDto.getCardOrder())//cardOrder를 차지하고 있는 카드가 있는지 확인
        .build();
    Card cardSaved = saveCard(card);
    return new CardResponseDto(cardSaved);
  }

  @Override
  public PageDto getAllCards(Pageable pageable, Long columnId) {
    Page<Card> result = cardRepository.findByColumnId(columnId,
        pageable); // commentId를 컨트롤러에서 받아서 repository에서 찾아야된다.
    var data = result.getContent().stream()
        .map(CardResponseDto::of)
        .toList();

    return new PageDto(data,
        result.getTotalElements(),
        result.getTotalPages(),
        pageable.getPageNumber(),
        data.size()
    );
  }

  public ResponseEntity<?> updateWorkerTransferCard(Long cardId, Long userId) {

    User user = userRepository.findById(getAuth().getUser().getUserId())
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
    saveCard(card);

    return ResponseEntity.ok().body("작업자가 변경되었습니다!");
  }


  @Override
  public ResponseEntity<?> updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto) {

    User user = userRepository.findById(getAuth().getUser().getUserId())
        .orElseThrow(() -> new IllegalArgumentException("선택한 유저는 존재하지 않습니다."));//선택한 유저 찾기

    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));//선택한 카드 찾기

    if (!card.getUser().getUserId().equals(user.getUserId())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 게시글을 수정할 수 없습니다.");
      //카드에 저장된 아이디와 현재 저장된 유저와 id 일치 여부 확인
    }

    card.update(cardUpdateRequestDto);
    saveCard(card);
    return ResponseEntity.ok().body("업데이트를 성공하였습니다.");
  }

  @Override
  public ResponseEntity<?> deleteCard(Long cardId) {
    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));
    cardRepository.delete(card);
    return ResponseEntity.ok().body("카드를 삭제 합니다.");
  }


  public CardResponseDto getCard(Long cardId) {
    Card card = getCardById(cardId);
    return new CardResponseDto(card);
  }

  @Override
  public Card getCardById(Long id) {
    return cardRepository.findById(id).orElseThrow
        (() -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));
  }

  @Override
  public Card saveCard(Card card) {
    return cardRepository.save(card);
  }

  public ResponseEntity<?> moveCard(Long cardId, Long targetColumnId, Long targetOrder) {

    Card card = cardRepository.findById(cardId).orElseThrow
        (() -> new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));//카드 조회
    Column targetColumn = columnRepository.findById(targetColumnId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 컬럼은 존재하지 않습니다."));//옮길 컬럼 존재 여부 조회

    List<Card> existingCards = cardRepository.findByColumn_IdAndCardOrderGreaterThanEqual(
        targetColumnId, targetOrder);//원하는 위치에 같거나 큰 카드들 List저장
    //컬럼안 카드 리스트 찾기
    for (Card cardOne : existingCards) {//list에서 카드들을 순회하면서 순서를 1씩 추가시켜줌
      cardOne.setCardOrder(cardOne.getCardOrder() + 1);
      cardRepository.save(cardOne);
    }
    card.columnForeign(targetColumn);//컬럼 저장
    card.setCardOrder(targetOrder);// 카드순서 저장
    cardRepository.save(card);//데이터 베이스 저장
    return ResponseEntity.ok().body(card);
  }

//  public ResponseEntity<CardTransferResponseDto> cardTransferResponseDto(Long cardId, Long columnId, String cardOrder){
//    ColumnEntity columnEntity = columnRepository.findById(columnId).orElseThrow(() -> new IllegalArgumentException("선택한 컬럼은 존재하지 않습니다."));
//    Card card = cardRepository.findById(cardId).orElseThrow(()->new IllegalArgumentException("선택한 카드는 존재하지 않습니다."));
//    List<ColumnEntity> result = commentRepository.findByPostId(postId, pageable);
//  }

}
