package com.example.toy_trello.domain.user;

import com.example.toy_trello.global.dto.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
        userService.signup(userSignupRequestDto);
        return ResponseEntity.ok().body(new CommonResponseDto("회원가입이 완료되었습니다.", HttpStatus.OK.value()));
    }
}
