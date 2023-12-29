package com.example.toy_trello.domain.card.service;

import com.example.toy_trello.domain.card.dto.CardCreateRequestDto;
import com.example.toy_trello.domain.card.dto.CardResponseDto;
import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j(topic = "CardService")
@RequiredArgsConstructor
@Service
public class CardService {

  private final CardRepository cardRepository;
  public CardResponseDto createCard(CardCreateRequestDto requestDto) {
    Card card = Card.builder()
        .cardName(requestDto.getCardName())
        .cardDescription(requestDto.getCardDescription())
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
 // }



  public ResponseEntity<?> updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto) {
    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));

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
