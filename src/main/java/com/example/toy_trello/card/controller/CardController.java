package com.example.toy_trello.card.controller;

import com.example.toy_trello.card.dto.CardCreateRequestDto;
import com.example.toy_trello.card.dto.CardResponseDto;
import com.example.toy_trello.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.card.dto.PageDto;
import com.example.toy_trello.card.service.CardService;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
  @GetMapping("/{cardId}")
  public CardResponseDto getPost(@PathVariable Long cardId) {
    return cardService.getCard(cardId);
  }

//  @GetMapping("/list")
//  public ResponseEntity<Page<CardResponseDto>> getCards(Pageable pageable) {
//    PageDto response = cardService.getCards(pageable);
//    return ResponseEntity.ok(response);
//  }

  @PostMapping
  public CardResponseDto createPost(@RequestBody CardCreateRequestDto postRequestDto)
      throws ParseException {
    return cardService.createCard(postRequestDto);
  }

  @PutMapping("/{cardId}")
  public ResponseEntity<?> updateCard(@PathVariable Long cardId,
      @RequestBody CardUpdateRequestDto postUpdateRequestDto) {
    return cardService.updateCard(cardId, postUpdateRequestDto);
  }

  @DeleteMapping("/{cardId}")
  public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
    return cardService.deleteCard(cardId);
  }

//  @PatchMapping("/{cardId}")
//  public ResponseEntity<?> transferCard(@PathVariable Long cardId){
//
//  }
}
