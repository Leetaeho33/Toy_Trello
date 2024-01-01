package com.example.toy_trello.domain.team.dto;

import com.example.toy_trello.domain.member.dto.MemberResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamResponseDto {
    String teamName;
    String description;
    List<MemberResponseDto> memberResponseDtos;

    public TeamResponseDto(String teamName, String description, List<MemberResponseDto> memberResponseDtos) {
        this.teamName = teamName;
        this.description = description;
        this.memberResponseDtos = memberResponseDtos;
    }
}
