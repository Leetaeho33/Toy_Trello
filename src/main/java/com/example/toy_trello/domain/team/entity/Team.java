package com.example.toy_trello.domain.team.entity;

import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.member.entity.Member;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@Getter
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String teamName;

    @Column
    private String description;


    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Member> members = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Team(Board board, String teamName, String description){
        this.description = description;
        this.teamName = teamName;
        this.board = board;
    }
}
