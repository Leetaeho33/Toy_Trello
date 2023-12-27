package com.example.toy_trello.card.repository;

import com.example.toy_trello.card.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
//  @EntityGraph(attributePaths = {"user"})
//  Page<Card> findByColumnId(Long columnId, Pageable pageable);
}
