package com.example.toy_trello.domain.member.repository;


import com.example.toy_trello.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
