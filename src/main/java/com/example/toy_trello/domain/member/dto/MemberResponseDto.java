package com.example.toy_trello.domain.member.dto;

import com.example.toy_trello.domain.member.entity.MemberRole;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    String memberName;
    MemberRole memberRole;

    public MemberResponseDto(String memberName, MemberRole memberRole) {
        this.memberName = memberName;
        this.memberRole = memberRole;
    }
}
