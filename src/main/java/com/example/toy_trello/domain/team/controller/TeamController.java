package com.example.toy_trello.domain.team.controller;

import com.example.toy_trello.domain.member.dto.MemberResponseDto;
import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.dto.TeamMemberRequestDto;
import com.example.toy_trello.domain.team.service.TeamService;
import com.example.toy_trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vi/api/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/board/{boardId}")
    public ResponseEntity<TeamCreateResponseDto> create(@RequestBody TeamCreateRequestDto teamCreateRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.createTeam(teamCreateRequestDto,userDetails.getUser(), boardId));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<List<MemberResponseDto>> inviteMember
                                                        (@RequestBody TeamMemberRequestDto teamMemberRequestDto,
                                                        @PathVariable Long teamId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(teamService.inviteMember(teamMemberRequestDto,
                                                        teamId, userDetails));
    }

}
