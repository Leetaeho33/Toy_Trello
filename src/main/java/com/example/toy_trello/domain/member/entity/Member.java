package com.example.toy_trello.domain.member.entity;

import com.example.toy_trello.domain.team.entity.Team;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Member(String role, User user) {
        this.role = role;
        this.user = user;

    }
    public void setTeam(Team team){
        this.team = team;
    }
    public void updateRole(String role){
        this.role = role;
    }
}
