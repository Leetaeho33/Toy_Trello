package com.example.toy_trello.domain.team.service;

import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.board.repository.BoardRepository;
import com.example.toy_trello.domain.member.dto.MemberResponseDto;
import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.member.repository.MemberRepository;
import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.dto.TeamMemberRequestDto;
import com.example.toy_trello.domain.team.dto.TeamResponseDto;
import com.example.toy_trello.domain.team.entity.Team;
import com.example.toy_trello.domain.team.repository.TeamRepository;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j(topic ="TeamService")
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // default : 팀을 생성한 자가 팀 리더
    @Transactional
    public TeamCreateResponseDto createTeam(TeamCreateRequestDto teamCreateRequestDto, User user, Long boardId) {
        log.info("팀 생성");
        String teamName = teamCreateRequestDto.getTeamName();
        String description = teamCreateRequestDto.getDescription();
        Member member = new Member("leader", user);
        Board board = findBoardById(boardId);

        if(checkDuplicatedTeamName(teamName)){
            Team team = Team.builder().teamName(teamName).description(description)
                    .board(board).build();
            teamRepository.save(team);
            team.getMembers().add(member);
            member.setTeam(team);
        }
        log.info("팀 생성 완료");
        return new TeamCreateResponseDto(teamName, description, board.getBoardName(), member);
    }

    // 팀장만 팀원 초대 가능
    // 한팀에 팀장은 한명
    public TeamResponseDto inviteMember(TeamMemberRequestDto teamMemberRequestDto,
                                        Long teamId, User user) {
        log.info("멤버 초대");
        User requestMember = findUserById(teamMemberRequestDto.getUsername());
        String role = teamMemberRequestDto.getRole();
        Member member = new Member(role, requestMember);
        Team team = findTeamById(teamId);
        //팀장이 중복인지?
        if(checkDuplicatedLeader(teamId, teamMemberRequestDto.getRole())){
            //한팀에 중복된 멤버가 있는지
            if(checkDuplicatedMember(teamId, requestMember)){
                //팀원을 초대할 권한이 있는지
                if(checkLeaderAuthorization(teamId, user)){
                    member.setTeam(team);
                    memberRepository.save(member);
                }
            }
        }

        return new TeamResponseDto(team.getTeamName(),
                team.getDescription(), transEntityToDtoList(team));
    }

    // 팀장만 팀원 추방 가능
    public TeamResponseDto exileMember(Long teamId, Long memberId, User user) {
        log.info("멤버 추방");
        Team team = findTeamById(teamId);
        Member member = findByMemberId(memberId);
        if(checkLeaderAuthorization(teamId, user)){
            memberRepository.delete(member);
        }
        return new TeamResponseDto(team.getTeamName(), team.getDescription(), transEntityToDtoList(team));
    }


    // 중복 체크 메소드(중복이면 예외 던짐, 중복이 아니면 true 리턴)
    public boolean checkDuplicatedTeamName(String teamName){
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

    public User findUserById(String username){
        log.info("유저 조회");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            log.error("유저가 존재하지 않습니다.");
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
    }

    public Team findTeamById(Long id){
        log.info("팀 조회");
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if(optionalTeam.isPresent()){
            return optionalTeam.get();
        }else {
            log.error("팀이 존재하지 않습니다.");
            throw new IllegalArgumentException("팀이 존재하지 않습니다.");
        }
    }

    private Member findByMemberId(Long memberId) {
        log.info("멤버 조회");
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isPresent()){
            return optionalMember.get();
        }else {
            log.error("멤버가 존재하지 않습니다.");
            throw new IllegalArgumentException("멤버가 존재하지 않습니다.");
        }
    }

    public boolean checkDuplicatedLeader(Long teamId, String role){
        log.info("팀장은 팀당 한명뿐.");
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        List<Member> members;
        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            members = team.getMembers();
            for(Member member : members){
                if(member.getRole().equals("leader") && role.equals("leader")){
                    log.error("한 팀에 팀장은 한명뿐입니다!");
                    throw new IllegalArgumentException("한 팀에 팀장은 한명뿐입니다!");
                }
            }
        }
        return true;
    }

    public boolean checkDuplicatedMember(Long teamId, User user){
        log.info("중복 멤버 체크.");
        Team team = findTeamById(teamId);
        List<Member> members;
        members = team.getMembers();
        for(Member member : members){
            if(member.getUser().getUserId().equals(user.getUserId())){
                log.error("중복된 인원이 한팀에는 들어갈 수 없습니다.");
                throw new IllegalArgumentException("중복된 인원이 한팀에는 들어갈 수 없습니다.");
            }
        }
        return true;
    }

    public List<MemberResponseDto> transEntityToDtoList(Team team){
        log.info("Member Entity -> dto 변환");
        List<Member> members = team.getMembers();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        if(!members.isEmpty()){
            for(Member member : members){
                MemberResponseDto memberResponseDto = new MemberResponseDto(member.getUser().getUsername(), member.getRole());
                memberResponseDtos.add(memberResponseDto);
            }
        }
        return memberResponseDtos;
    }

    //
    public boolean checkLeaderAuthorization(Long teamId, User teamLeader){
        log.info("팀 리더 권한 체크");
        List<Member> teamMember = memberRepository.findByTeam_Id(teamId);
        Member member = null;
        for(Member m : teamMember){
            if(m.getUser().getUserId().equals(teamLeader.getUserId())){
                member = m;
                break;
            }
        }
        // 초대하는 사람이 팀 멤버가 아니면 null -> 예외
        if(member!=null) {
            String role = member.getRole();
            // 초대하는 사람의 권한 체크 : Leader만 초대 가능
            if (member.getTeam().getId() == teamId && role.equals("leader")) {
                log.info("팀장은 가능한 권한 있습니다.");
                return true;
            } else {
                log.error("팀장만 초대할 수 있습니다.");
                throw new IllegalArgumentException("팀장만 접근 할 수 있습니다.");
            }
        }else throw new IllegalArgumentException("로그인된 유저는 이 팀의 멤버가 아닙니다.");
    }

}
