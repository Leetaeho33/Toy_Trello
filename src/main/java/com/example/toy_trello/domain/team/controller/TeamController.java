package com.example.toy_trello.domain.team.controller;

import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.service.TeamService;
import com.example.toy_trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/board/{boardId}")
    public ResponseEntity<TeamCreateResponseDto> create(@RequestBody TeamCreateRequestDto teamCreateRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long boardId){
        return ResponseEntity.status(HttpStatus.OK).
                body(teamService.createTeam(teamCreateRequestDto,userDetails.getUser(), boardId));
    }

}
