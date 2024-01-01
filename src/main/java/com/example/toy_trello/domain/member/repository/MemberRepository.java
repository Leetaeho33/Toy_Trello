package com.example.toy_trello.domain.member.repository;


import com.example.toy_trello.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findByTeam_Id(Long teamId);
}
