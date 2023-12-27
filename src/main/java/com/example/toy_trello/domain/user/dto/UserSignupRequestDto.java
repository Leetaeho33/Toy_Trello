package com.example.toy_trello.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "username은 최소 3자이상, 영문 대소문자나 숫자만 입력 가능합니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "password는 최소 4자 이상, 영문 대소문자나 숫자만 입력 가능합니다.")
    private String password;

    private String checkPassword;

    @Pattern(regexp = "^.{0,500}$", message = "자기소개는 500자 이하만 입력 가능합니다.")
    private String intro;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 주소를 입력하세요.")
    private String email;
}
