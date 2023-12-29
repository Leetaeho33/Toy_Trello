package com.example.toy_trello.domain.user;

import com.example.toy_trello.domain.board.entity.Board;
import com.example.toy_trello.domain.user.dto.UserSignupRequestDto;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String intro;
    @Column(nullable = false)
    private String email;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Board> boards = new HashSet<>();

    public User(UserSignupRequestDto userRequestDto, String encodedPassword) {
        this.username = userRequestDto.getUsername();
        this.password = encodedPassword;
        this.intro = userRequestDto.getIntro();
        this.email = userRequestDto.getEmail();
    }
}
