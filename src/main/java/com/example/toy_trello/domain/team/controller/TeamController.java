package com.example.toy_trello.domain.team.controller;

import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.dto.TeamMemberRequestDto;
import com.example.toy_trello.domain.team.dto.TeamResponseDto;
import com.example.toy_trello.domain.team.service.TeamService;
import com.example.toy_trello.global.dto.CommonResponseDto;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getTeamMember(@PathVariable long teamId){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.getTeamMember(teamId));
    }


    @PutMapping("/invite/{teamId}")
    public ResponseEntity<CommonResponseDto> inviteMember(@RequestBody TeamMemberRequestDto teamMemberRequestDto,
                                                        @PathVariable Long teamId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        teamService.inviteMember(teamMemberRequestDto, teamId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).
                body(new CommonResponseDto("팀원 초대가 성공했습니다.", HttpStatus.OK.value()));
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

    @DeleteMapping("/delete/team/{teamId}")
    public ResponseEntity<CommonResponseDto> deleteTeam(@PathVariable Long teamId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        teamService.deleteTeam(teamId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).
                body(new CommonResponseDto("팀이 삭제 되었습니다.", HttpStatus.OK.value()));
    }

}
