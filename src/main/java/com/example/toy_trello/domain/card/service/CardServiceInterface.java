package com.example.toy_trello.domain.card.service;

import com.example.toy_trello.column.entity.Column;
import com.example.toy_trello.domain.card.dto.CardCreateRequestDto;
import com.example.toy_trello.domain.card.dto.CardResponseDto;
import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.card.dto.PageDto;
import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.team.entity.Team;
import com.example.toy_trello.global.security.UserDetailsImpl;
import java.util.List;
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
