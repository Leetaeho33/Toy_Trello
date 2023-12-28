package com.example.toy_trello.domain.user;

import com.example.toy_trello.domain.user.dto.UserProfileRequestDto;
import com.example.toy_trello.domain.user.dto.UserSignupRequestDto;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.*;
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

    public User(UserSignupRequestDto userRequestDto, String encodedPassword) {
        this.username = userRequestDto.getUsername();
        this.password = encodedPassword;
        this.intro = userRequestDto.getIntro();
        this.email = userRequestDto.getEmail();
    }

    public User update(UserProfileRequestDto userProfileRequestDto) {
        this.intro = userProfileRequestDto.getIntro() == null ? this.getIntro() : userProfileRequestDto.getIntro();
        this.email = userProfileRequestDto.getEmail() == null ? this.getEmail() : userProfileRequestDto.getEmail();
        return this;
    }
}
