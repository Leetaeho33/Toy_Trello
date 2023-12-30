package com.example.toy_trello.domain.team.service;

import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.board.repository.BoardRepository;
import com.example.toy_trello.domain.member.dto.MemberResponseDto;
import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.member.exception.MemberNotFoundException;
import com.example.toy_trello.domain.member.exception.NotExistMemberException;
import com.example.toy_trello.domain.member.repository.MemberRepository;
import com.example.toy_trello.domain.team.dto.TeamCreateRequestDto;
import com.example.toy_trello.domain.team.dto.TeamCreateResponseDto;
import com.example.toy_trello.domain.team.dto.TeamMemberRequestDto;
import com.example.toy_trello.domain.team.dto.TeamResponseDto;
import com.example.toy_trello.domain.team.entity.Team;
import com.example.toy_trello.domain.team.exception.*;
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

import static com.example.toy_trello.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.example.toy_trello.domain.member.exception.MemberErrorCode.NOT_EXIST_MEMBER;
import static com.example.toy_trello.domain.team.exception.TeamErrorCode.*;

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
    public TeamResponseDto inviteMember(TeamMemberRequestDto teamMemberRequestDto,
                                        Long teamId, User user) {
        log.info("멤버 초대");
        User requestMember = findUserById(teamMemberRequestDto.getUsername());
        Member member = new Member("teamMember", requestMember);
        Team team = findTeamById(teamId);
        //한팀에 중복된 멤버가 있는지
        if(checkDuplicatedMember(teamId, requestMember)){
                //팀원을 초대할 권한이 있는지
            if(checkLeaderAuthorization(teamId, user)){
                member.setTeam(team);
                memberRepository.save(member);
            }
        }
        return new TeamResponseDto(team.getTeamName(),
                team.getDescription(), transEntityToDtoList(team));
    }

    // 팀장만 팀원 추방 가능
    public TeamResponseDto exileMember(Long teamId, Long memberId, User teamLeader) {
        log.info("멤버 추방");
        Team team = findTeamById(teamId);
        Member member = findByMemberId(memberId);
        if(checkLeaderAuthorization(teamId, teamLeader)){
            memberRepository.delete(member);
        }
        return new TeamResponseDto(team.getTeamName(), team.getDescription(), transEntityToDtoList(team));
    }

    @Transactional
    public TeamResponseDto changeRole(Long teamId, Long memberId, User currentLeader) {
        Team team = findTeamById(teamId);
        Member currentLeaderMember = findMemberByUser(teamId, currentLeader);
        Member newLeader = findByMemberId(memberId);
        swapRole(newLeader, currentLeaderMember, teamId);
        return new TeamResponseDto(team.getTeamName(), team.getDescription(), transEntityToDtoList(team));
    }

    public TeamResponseDto leaveTeam(Long teamId, User user) {
        log.info("팀 탈퇴");
        Team team = findTeamById(teamId);
        Member member = findMemberByUser(teamId, user);
        log.info("팀 id는 "+ team.getId());
        log.info("멤버 이름은 " + member.getUser().getUsername());
        log.info("멤버 id는 " + member.getId());
        memberRepository.delete(member);
            if(!checkLeaderExist(teamId)){
                log.info("팀장이 팀 탈퇴시");
                Member newLeader = findNewLeader(teamId);
                // 업데이트를 할 때 transaction을 걸면 좋은데 여기선 delete와 함께 있어서 transaction을 못검..
                // 이거 잘 하면 transaction걸고 할 수 있을 것 같은데 모르겠습니다 ㅠ
                newLeader.updateRole("leader");
                memberRepository.save(newLeader);
            }else memberRepository.delete(member);
        log.info("팀 탈퇴 완료");
        return new TeamResponseDto(team.getTeamName(), team.getDescription(), transEntityToDtoList(team));
    }

    public TeamResponseDto setLeader(Long teamId, Long memberId) {
        Team team = findTeamById(teamId);
        return null;
    }

    // 팀 이름 중복 체크 메소드(중복이면 예외 던짐, 중복이 아니면 true 리턴)
    public boolean checkDuplicatedTeamName(String teamName){
        log.info("팀 중복 체크");
        Optional<Team> team = teamRepository.findByTeamName(teamName);
        if(team.isPresent()){
            log.error("이미 존재하는 팀입니다.");
            throw new DuplicatedTeamException(DUPLICATED_TEAM);
        }
        else return true;
    }

    //Board 도메인의 예외 사용
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

    //User 도메인의 예외 사용
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
            throw new TeamNotFoundException(TEAM_NOT_FOUND);
        }
    }

    private Member findByMemberId(Long memberId) {
        log.info("멤버 조회");
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isPresent()){
            return optionalMember.get();
        }else {
            log.error("멤버가 존재하지 않습니다.");
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }
    }

    public boolean checkLeaderExist(Long teamId){
        log.info("팀장 유무 체크");
        Team team = findTeamById(teamId);
        List<Member> members = team.getMembers();
        for(Member member : members){
            if(member.getRole().equals("leader")){
                return true;
            }
        }
        return false;
    }

    public boolean checkDuplicatedMember(Long teamId, User user){
        log.info("중복 멤버 체크.");
        Team team = findTeamById(teamId);
        List<Member> members;
        members = team.getMembers();
        for(Member member : members){
            if(member.getUser().getUserId().equals(user.getUserId())){
                log.error("중복된 인원이 한팀에는 들어갈 수 없습니다.");
                throw new DuplicatedMemberException(DUPLICATED_MEMBER);
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
        }else {
            log.error("멤버가 한명도 존재하지 않습니다.");
            throw new NotExistMemberException(NOT_EXIST_MEMBER);
        }
        return memberResponseDtos;
    }

    public Member findMemberByUser(Long teamId, User user) {
        log.info("해당 유저가 팀의 어떤 멤버인지 찾기");
        List<Member> teamMember = memberRepository.findByTeam_Id(teamId);
        Member member = null;
        for (Member m : teamMember) {
            if (m.getUser().getUserId().equals(user.getUserId())) {
                member = m;
                return member;
            }
        }
        if(member==null) throw new NotTeamMemberException(NOT_TEAM_MEMBER);
        return null;
    }

    public boolean checkLeaderAuthorization(Long teamId, User teamLeader){
        log.info("팀 리더 권한 체크");
        Member member = findMemberByUser(teamId, teamLeader);
        String role = member.getRole();
        // 초대하는 사람의 권한 체크 : Leader만 초대 가능
        if (member.getTeam().getId() == teamId && role.equals("leader")) {
            log.info("팀장은 가능한 권한 입니다.");
            return true;
        } else {
            log.error("팀장에게만 있는 권한입니다.");
            throw new UnAuthorizationException(ONLY_AUTHORIZED_LEADER);
        }
    }
    public void swapRole(Member newLeader, Member currentLeader, Long teamId){
        log.info("팀장 변경");
        if(checkLeaderAuthorization(teamId, currentLeader.getUser())){
            currentLeader.updateRole("teamMember");
            newLeader.updateRole("leader");
        }
        log.info("팀장 변경 완료");
    }

    public Member findNewLeader(Long teamId){
        Optional<Member> member = memberRepository.findFirstByTeamId(teamId);
        if(member.isPresent()){
            return member.get();
        }else {
            log.error("멤버가 한명도 존재하지 않습니다.");
            return null;
        }
    }

}
