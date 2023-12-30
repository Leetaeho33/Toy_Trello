package com.example.toy_trello.domain.team.controller;

import com.example.toy_trello.domain.member.dto.MemberResponseDto;
import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.dto.TeamMemberRequestDto;
import com.example.toy_trello.domain.team.dto.TeamResponseDto;
import com.example.toy_trello.domain.team.service.TeamService;
import com.example.toy_trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/board/{boardId}")
    public ResponseEntity<TeamCreateResponseDto> create(@RequestBody TeamCreateRequestDto teamCreateRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.createTeam(teamCreateRequestDto,userDetails.getUser(), boardId));
    }

    @PutMapping("/invite/{teamId}")
    public ResponseEntity<TeamResponseDto> inviteMember(@RequestBody TeamMemberRequestDto teamMemberRequestDto,
                                                        @PathVariable Long teamId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(teamService.inviteMember(teamMemberRequestDto,
                                                        teamId, userDetails.getUser()));
    }
    @DeleteMapping("/exile/team/{teamId}/member/{memberId}")
    public ResponseEntity<TeamResponseDto> exileMember(@PathVariable Long teamId, @PathVariable Long memberId,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.exileMember(teamId, memberId, userDetails.getUser()));
    }

    @PutMapping("/role/team/{teamId}/member/{memberId}")
    public ResponseEntity<TeamResponseDto> changeRole(@PathVariable Long teamId,
                                                      @PathVariable Long memberId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.changeRole(teamId, memberId, userDetails.getUser()));
    }

    @DeleteMapping("leave/team/{teamId}")
    public ResponseEntity<?> leaveTeam(@PathVariable Long teamId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.leaveTeam(teamId, userDetails.getUser()));
    }

}
