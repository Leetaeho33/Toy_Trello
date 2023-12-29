package com.example.toy_trello.domain.card.repository;

import com.example.toy_trello.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
//  @EntityGraph(attributePaths = {"user"})
//  Page<Card> findByColumnId(Long columnId, Pageable pageable);
}
