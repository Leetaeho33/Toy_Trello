package com.example.toy_trello.domain.card.service;

import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.card.dto.PageDto;
import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CardServiceInterface {
  UserDetailsImpl getAuth();
  PageDto getAllCards(Pageable pageable,Long columnId);
  Card getCardById(Long Id);
  Card saveCard(Card card);

  ResponseEntity<?> updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto, Long teamId);
  ResponseEntity<?> deleteCard(Long id,Long teamId);

}
