package com.example.toy_trello.domain.card.controller;

import com.example.toy_trello.domain.card.dto.CardCreateRequestDto;
import com.example.toy_trello.domain.card.dto.CardResponseDto;
import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.card.dto.ColumnWithCardsResponseDto;
import com.example.toy_trello.domain.card.dto.PageDto;
import com.example.toy_trello.domain.card.service.CardService;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "CardController")
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@RestController
public class CardController {

  private final CardService cardService;


  @PostMapping
  public CardResponseDto createCard(@RequestBody CardCreateRequestDto postRequestDto)
      throws ParseException {

    return cardService.createCard(postRequestDto);
  }
  @GetMapping("/{cardId}")
  public CardResponseDto getCard(@PathVariable Long cardId) {

    return cardService.getCard(cardId);
  }

  @GetMapping("/list/{columnId}")
  public ResponseEntity<PageDto> getCards(Pageable pageable, @PathVariable Long columnId) {
    PageDto response = cardService.getAllCards(pageable, columnId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{cardId}/team/{teamId}")
  public ResponseEntity<?> updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto postUpdateRequestDto,@PathVariable Long teamId) {
    return cardService.updateCard(cardId, postUpdateRequestDto,teamId);
  }

  @DeleteMapping("/{cardId}/team/{teamId}")
  public ResponseEntity<?> deleteCard(@PathVariable Long cardId,@PathVariable Long teamId) {
    return cardService.deleteCard(cardId, teamId);
  }


  @PutMapping("/card/{cardId}/user/{userId}/team/{teamId}")
  public ResponseEntity<?> updateWorkerTransferCard(@PathVariable Long cardId,
      @PathVariable Long userId,@PathVariable Long teamId) {
    return cardService.updateWorkerTransferCard(cardId, userId,teamId);
  }

  @PutMapping("/{cardId}/columns/{targetColumnId}/order/{order}")
  public ResponseEntity<?> moveCard(
      @PathVariable Long cardId,
      @PathVariable Long targetColumnId,
      @PathVariable Long order) {
    return cardService.moveCard(cardId,targetColumnId,order);
  }

  @GetMapping("/columns/{columnId}")
  public ResponseEntity<ColumnWithCardsResponseDto> cardListInAColumn(@PathVariable Long columnId,Pageable pageable){
    return cardService.cardListInAColumn(columnId,pageable);
  }

}
