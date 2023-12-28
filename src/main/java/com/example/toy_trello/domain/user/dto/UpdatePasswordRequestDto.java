package com.example.toy_trello.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "password는 최소 4자 이상, 영문 대소문자나 숫자만 입력 가능합니다.")
    private String newPassword;
    private String checkNewPassword;
}
