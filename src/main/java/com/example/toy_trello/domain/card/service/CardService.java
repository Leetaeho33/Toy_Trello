package com.example.toy_trello.domain.card.service;

import static com.example.toy_trello.domain.card.exception.CardErrorCode.*;
import static com.example.toy_trello.domain.team.exception.TeamErrorCode.TEAM_NOT_FOUND;

import com.example.toy_trello.domain.column.entity.Column;
import com.example.toy_trello.domain.column.repository.ColumnRepository;
import com.example.toy_trello.domain.card.dto.CardCreateRequestDto;
import com.example.toy_trello.domain.card.dto.CardListResponseDto;
import com.example.toy_trello.domain.card.dto.CardMoveResponseDto;
import com.example.toy_trello.domain.card.dto.CardResponseDto;
import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.card.dto.ColumnWithCardsResponseDto;
import com.example.toy_trello.domain.card.dto.PageDto;
import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.card.exception.CardErrorCode;
import com.example.toy_trello.domain.card.exception.CardExistsException;
import com.example.toy_trello.domain.card.exception.ColumnExistsException;
import com.example.toy_trello.domain.card.exception.SameCardException;
import com.example.toy_trello.domain.card.exception.SameIdMaxCardOrderException;
import com.example.toy_trello.domain.card.exception.UserExistsException;
import com.example.toy_trello.domain.card.repository.CardRepository;
import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.member.entity.MemberRole;
import com.example.toy_trello.domain.member.repository.MemberRepository;
import com.example.toy_trello.domain.team.entity.Team;
import com.example.toy_trello.domain.team.exception.TeamNotFoundException;
import com.example.toy_trello.domain.team.repository.TeamRepository;
import com.example.toy_trello.domain.team.service.TeamService;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  private final MemberRepository memberRepository;

  private final TeamRepository teamRepository;
  private final TeamService teamService;

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
    Team team = teamRepository.findById(requestDto.getTeamId())
        .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
    Column column = columnRepository.findById(requestDto.getColumnId())
        .orElseThrow(() -> new ColumnExistsException(COLUMN_NOT_FOUND));
    Optional<Card> cardInColumn = cardRepository.findFirstByColumn_IdOrderByCardOrderDesc(
        requestDto.getColumnId());
    //컬럼id를 통해서 카드를 찾기

    if(cardInColumn.isEmpty()){
      requestDto.setCardOrder(1);
      Card card = Card.builder()
          .column(column)
          .cardName(requestDto.getCardName())
          .cardDescription(requestDto.getCardDescription())
          .user(getAuth().getUser())
          .dueDate(requestDto.getDueDate())
          .cardColor(requestDto.getCardColor())
          .cardOrder(requestDto.getCardOrder())//cardOrder를 차지하고 있는 카드가 있는지 확인
          .team(team)
          .build();
      Card cardSaved = saveCard(card);
      return new CardResponseDto(cardSaved);
    }
    else {
      if (requestDto.getCardOrder() > cardInColumn.get().getCardOrder() + 1) {
        requestDto.setCardOrder(cardInColumn.get().getCardOrder() + 1);
      }

      insertCard(requestDto.getColumnId(), requestDto.getCardOrder());
      //cardOrder를 마지막보다 +1이 아닌 그 이상 했을 경우 +1을 저장하도록 만든다.
      Card card = Card.builder()
          .column(column)
          .cardName(requestDto.getCardName())
          .cardDescription(requestDto.getCardDescription())
          .user(getAuth().getUser())
          .dueDate(requestDto.getDueDate())
          .cardColor(requestDto.getCardColor())
          .cardOrder(requestDto.getCardOrder())//cardOrder를 차지하고 있는 카드가 있는지 확인
          .team(team)
          .build();
      Card cardSaved = saveCard(card);
      return new CardResponseDto(cardSaved);
    }
  }

  @Override
  public PageDto getAllCards(Pageable pageable, Long columnId) {
    Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("cardOrder").ascending());
    Page<Card> result = cardRepository.findByColumnId(columnId,
        sortedPageable); // commentId를 컨트롤러에서 받아서 repository에서 찾아야된다.
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

  public ResponseEntity<?> updateWorkerTransferCard(Long cardId, Long userId,Long teamId) {

      User user = userRepository.findById(getAuth().getUser().getUserId())//현재 로그인 된 유저정보
        .orElseThrow(() -> new UserExistsException(USER_NOT_FOUND));//선택한 유저 찾기

    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new CardExistsException(CARD_NOT_FOUND));//선택한 카드 찾기

    Member cardMemberRole = teamService.findMemberByUser(teamId, user);

    if (!card.getUser().getUserId().equals(user.getUserId()) && !cardMemberRole.getRole().equals( MemberRole.LEADER)) {
      /**
       * 로그인 된 유저가 팀 리더인지 확인함
       * 로그인 된 유저가 카드 소유권을 가진지 확인함
       * 두가지 모두 거짓이면 카드를 수정할 수 없다.
       * @return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 카드를 수정할 수 없습니다.");
      */

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 카드를 수정할 수 없습니다.");
    }

    User userUpdated = userRepository.findById(userId)//옮겨질 유저 정보
        .orElseThrow(() -> new UserExistsException(USER_NOT_FOUND));
  // userUpdated 부분을 memberUpdated로 수정함
    card.userUpdate(userUpdated);
    saveCard(card);

    return ResponseEntity.ok().body("작업자가 변경되었습니다!");
  }


  @Override
  public ResponseEntity<?> updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto,Long teamId) {

    //MemberRole cardMemberRole = MemberRole.MEMBER;

    User user = userRepository.findById(getAuth().getUser().getUserId())
        .orElseThrow(() -> new UserExistsException(USER_NOT_FOUND));//선택한 유저 찾기

    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new CardExistsException(CARD_NOT_FOUND));//선택한 카드 찾기

    Member cardMemberRole = teamService.findMemberByUser(teamId, user);



    if (!card.getUser().getUserId().equals(user.getUserId()) && !cardMemberRole.getRole().equals( MemberRole.LEADER))
    {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 카드는 수정할 수 없습니다.");
      //카드에 저장된 아이디와 현재 저장된 유저와 id 일치 여부 확인
    }

    card.update(cardUpdateRequestDto);
    saveCard(card);
    return ResponseEntity.ok().body("업데이트를 성공하였습니다.");
  }

  @Override
  public ResponseEntity<?> deleteCard(Long cardId,Long teamId) {

//    Member cardMemberRole = MemberRole.MEMBER;

    Team team = teamRepository.findById(teamId)
        .orElseThrow(()->new TeamNotFoundException(TEAM_NOT_FOUND));

    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new CardExistsException(CARD_NOT_FOUND));
    User user = userRepository.findById(getAuth().getUser().getUserId())
        .orElseThrow(() -> new UserExistsException(USER_NOT_FOUND));//선택한 유저 찾기

    Member cardMemberRole = teamService.findMemberByUser(teamId, user);

//    List<Member> members = card.getTeam().getMembers();
//
//    for(Member theMember : members){ // 멤버중 리더 찾기
//      if(theMember.getRole().equals(MemberRole.LEADER)){
//        cardMemberRole = MemberRole.LEADER;
//        break;
//      }
//    }



    if (!card.getUser().getUserId().equals(user.getUserId()) && !cardMemberRole.getRole().equals( MemberRole.LEADER))
    {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사용자의 카드는 삭제할 수 없습니다.");
      //카드에 저장된 아이디와 현재 저장된 유저와 id 일치 여부 확인
    }

    List<Card> cardList = cardRepository.findByCardOrderGreaterThan(card.getCardOrder());
    for(Card subCard: cardList){
      subCard.setCardOrder(subCard.getCardOrder()-1);
      cardRepository.save(subCard);
    } //카드 없어진 자리에 바로 위부터 카드 마지막까지 -1을 한다.

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
        (() -> new CardExistsException(CARD_NOT_FOUND));
  }

  @Override
  public Card saveCard(Card card) {
    return cardRepository.save(card);
  }

  public ResponseEntity<?> moveCard(Long cardId, Long targetColumnId, Long targetOrder) {

    Card card = cardRepository.findById(cardId).orElseThrow
        (() -> new CardExistsException(CARD_NOT_FOUND));//카드 조회
    Column targetColumn = columnRepository.findById(targetColumnId)
        .orElseThrow(() -> new ColumnExistsException(COLUMN_NOT_FOUND));//옮길 컬럼 존재 여부 조회


    //추가하려는 컬럼의 카드의 cardOrder가 기존 cardOrder보다 2이상 크다면 +1로 줄이기
    Optional<Card> cardOrderGreat = cardRepository.findFirstByColumn_IdOrderByCardOrderDesc(targetColumnId);

    if(cardOrderGreat.isEmpty()){
      card.columnForeign(targetColumn);
      card.setCardOrder(1L);
      cardRepository.save(card);
      cardOrderGreat = cardRepository.findFirstByColumn_IdOrderByCardOrderDesc(targetColumnId);
      return ResponseEntity.ok().body(CardMoveResponseDto.builder()
          .columnId(card.getColumn().getId())//컬럼 아이디 반환
          .columnName(card.getColumn().getName())//컬럼 이름 반환
          .cardId(card.getCardId())//카드 id 반환
          .cardOrder(card.getCardOrder())//카드 순서 반환
          .build()
      );
    }


    if (Objects.equals(targetOrder, card.getCardOrder()) && Objects.equals(card.getColumn().getId(), targetColumnId)) {
      throw new SameCardException(CardErrorCode.SAME_CARD);
    } else if (cardId.equals(cardOrderGreat.get().getCardId()) && cardOrderGreat.get().getCardOrder() <= targetOrder) {
      throw new SameIdMaxCardOrderException(CardErrorCode.SAME_ID_MAX_CARD_ORDER);
    }


    else if(Objects.equals(card.getColumn().getId(),targetColumnId) && cardOrderGreat.get().getCardOrder() <= targetOrder){
      Long temp = cardOrderGreat.get().getCardOrder();
      cardOrderGreat.get().setCardOrder(card.getCardOrder());
      card.setCardOrder(temp);
      cardRepository.save(card);
      return ResponseEntity.ok().body(CardMoveResponseDto.builder()
          .columnId(card.getColumn().getId())//컬럼 아이디 반환
          .columnName(card.getColumn().getName())//컬럼 이름 반환
          .cardId(card.getCardId())//카드 id 반환
          .cardOrder(card.getCardOrder())//카드 순서 반환
          .build()
      );
    }
    else if(Objects.equals(card.getColumn().getId(),targetColumnId)){
      Card cardSwitch = cardRepository.findByColumn_IdAndCardOrder(targetColumnId, targetOrder)
          .orElseThrow(()-> new CardExistsException(CARD_NOT_FOUND));
      Long temp = card.getCardOrder();
      card.setCardOrder(targetOrder);
      cardSwitch.setCardOrder(temp);
      cardRepository.save(card);
      return ResponseEntity.ok().body(CardMoveResponseDto.builder()
          .columnId(card.getColumn().getId())
          .columnName(card.getColumn().getName())
          .cardId(card.getCardId())
          .cardOrder(card.getCardOrder())
          .build()
      );
    }

    if(targetOrder >= cardOrderGreat.get().getCardOrder()+1)//if(추가할 카드의 cardOrder > 기존 카드중 높은 cardOrder)
    {
      card.setCardOrder(cardOrderGreat.get().getCardOrder()+1);// 카드순서 저장
    }
    else {
      List<Card> existingCards = cardRepository.findByColumn_IdAndCardOrderGreaterThanEqual(
          targetColumnId, targetOrder);//원하는 위치에 같거나 큰 카드들 List저장
      //컬럼안 카드 리스트 찾기
      for (Card cardOne : existingCards) {//list에서 카드들을 순회하면서 순서를 1씩 추가시켜줌
        cardOne.setCardOrder(cardOne.getCardOrder() + 1);
        cardRepository.save(cardOne);
      }
      card.setCardOrder(targetOrder);

    }
    card.columnForeign(targetColumn);//컬럼 저장

    cardRepository.save(card);//데이터 베이스 저장
    return ResponseEntity.ok().body(CardMoveResponseDto.builder()
        .columnId(card.getColumn().getId())//컬럼 아이디 반환
        .columnName(card.getColumn().getName())//컬럼 이름 반환
        .cardId(card.getCardId())//카드 id 반환
        .cardOrder(card.getCardOrder())//카드 순서 반환
        .build()
    );
  }

  //한 컬럼에서 카드리스트를 카드 순서대로 출력함
  public ResponseEntity<ColumnWithCardsResponseDto> cardListInAColumn(Long columnId,Pageable pageable){
    Column column = columnRepository.findById(columnId)
        .orElseThrow(()->new ColumnExistsException(COLUMN_NOT_FOUND));
    Page<Card> result = cardRepository.findByColumn_IdOrderByCardOrderAsc(columnId, pageable);

    var data = result.getContent().stream()
        .map(card -> new CardListResponseDto(
            card.getCardId(),
            card.getCardName(),
            card.getCardOrder()))
        .collect(Collectors.toList());


    PageDto pageData = new PageDto(
        data,
        result.getTotalElements(),
        result.getTotalPages(),
        pageable.getPageNumber(),
        data.size());

    ColumnWithCardsResponseDto response = new ColumnWithCardsResponseDto(
        column.getId(),
        column.getName(),
        pageData
    );

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


}
