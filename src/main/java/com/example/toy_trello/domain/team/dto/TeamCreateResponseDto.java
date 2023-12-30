package com.example.toy_trello.domain.team.dto;

import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamCreateResponseDto {
    String teamName;
    String description;
    String BoardName;
    String creator;


    public TeamCreateResponseDto(String teamName, String description, String boardName, Member member) {
        this.teamName = teamName;
        this.description = description;
        this.BoardName = boardName;
        this.creator = member.getUser().getUsername();
    }
}
