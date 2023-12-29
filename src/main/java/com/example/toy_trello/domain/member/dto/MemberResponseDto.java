package com.example.toy_trello.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {
    String memberName;
    String memberRole;

    public MemberResponseDto(String memberName, String memberRole) {
        this.memberName = memberName;
        this.memberRole = memberRole;
    }
}
