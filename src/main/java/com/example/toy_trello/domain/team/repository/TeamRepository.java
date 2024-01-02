package com.example.toy_trello.domain.team.repository;

import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamName(String teamName);
}
