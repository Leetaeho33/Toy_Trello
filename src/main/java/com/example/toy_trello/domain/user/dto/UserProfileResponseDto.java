package com.example.toy_trello.domain.user.dto;

import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.global.dto.CommonResponseDto;
import lombok.Getter;

@Getter
public class UserProfileResponseDto extends CommonResponseDto {
    private String username;
    private String intro;
    private String email;

    public UserProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.intro = user.getIntro() == null ? "자기소개가 없습니다." : user.getIntro();
        this.email = user.getEmail();
    }
}
