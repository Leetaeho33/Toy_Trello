package com.example.toy_trello.domain.card.repository;

import com.example.toy_trello.domain.card.entity.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card,Long> {
  @EntityGraph(attributePaths = {"user"})
  Page<Card> findByColumnId(Long columnId, Pageable pageable);

  @EntityGraph(attributePaths = {"column"})
  Optional<Card> findByColumnId(Long columnId);

  // 컬럼 ID와 카드 순서를 기준으로 카드를 찾는 메서드
  Optional<Card> findByColumn_IdAndCardOrder(Long columnId, Long cardOrder);
  List<Card> findByColumn_IdAndCardOrderGreaterThanEqual(Long columnId, Long cardOrder);

  Page<Card> findByColumn_IdOrderByCardOrderAsc(Long columnId, Pageable pageable);

  List<Card> findByCardOrderGreaterThan(Long cardOrder);

  Optional<Card> findFirstByColumn_IdOrderByCardOrderDesc(Long columnId);

  Optional<Card> findByCardOrder(Long cardOrder);

  @EntityGraph(attributePaths = {"user"})
  @Query("SELECT c FROM Card c WHERE c.column.id = :columnId")
  List<Card> findByColumnIdWithEntityGraph(@Param("columnId") Long columnId);


}
