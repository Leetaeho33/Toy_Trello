package com.example.toy_trello.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserProfileRequestDto {
    @Pattern(regexp = "^.{0,500}$", message = "자기소개는 500자 이하만 입력 가능합니다.")
    private String intro;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 주소를 입력하세요.")
    private String email;
}
