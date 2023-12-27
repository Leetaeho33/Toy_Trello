package com.example.toy_trello.domain.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    private String username;
    private String password;
}
