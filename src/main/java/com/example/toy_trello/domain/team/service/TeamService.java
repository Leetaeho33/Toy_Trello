package com.example.toy_trello.domain.team.service;

import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.board.repository.BoardRepository;
import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.member.repository.MemberRepository;
import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.entity.Team;
import com.example.toy_trello.domain.team.repository.TeamRepository;
import com.example.toy_trello.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    // default : 팀을 생성한 자가 팀 리더
    @Transactional
    public TeamCreateResponseDto createTeam(TeamCreateRequestDto teamCreateRequestDto, User user, Long boardId) {
        String teamName = teamCreateRequestDto.getTeamName();
        String description = teamCreateRequestDto.getDescription();
        Member member = new Member("leader", user);
        Board board = findBoardById(boardId);

        if(checkDuplication(teamName)){
            Team team = Team.builder().teamName(teamName).description(description)
                    .board(board).build();
            teamRepository.save(team);
            team.getMembers().add(member);
            member.setTeam(team);
        }
        return new TeamCreateResponseDto(teamName, description, board.getBoardName(), member);
    }

    // 중복 체크 메소드(중복이면 예외 던짐, 중복이 아니면 true 리턴)
    public boolean checkDuplication(String teamName){
        log.info("팀 중복 체크");
        Optional<Team> team = teamRepository.findByTeamName(teamName);
        if(team.isPresent()){
            log.error("이미 존재하는 팀입니다.");
            throw new IllegalArgumentException("이미 존재하는 팀입니다.");
        }
        else return true;
    }
    public Board findBoardById(Long boardId){
        log.info("보드 조회");
        Optional<Board> OptionalBoard = boardRepository.findById(boardId);
        if(OptionalBoard.isPresent()){
            Board board = OptionalBoard.get();
            return board;
        }else {
            log.error("보드가 존재 하지 않습니다.");
            throw new IllegalArgumentException("존재하지 않는 보드입니다.");
        }
    }
}
