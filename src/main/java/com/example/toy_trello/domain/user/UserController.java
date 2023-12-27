package com.example.toy_trello.domain.user;

import com.example.toy_trello.domain.user.dto.UserLoginRequestDto;
import com.example.toy_trello.domain.user.dto.UserSignupRequestDto;
import com.example.toy_trello.global.dto.CommonResponseDto;
import com.example.toy_trello.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
        userService.signup(userSignupRequestDto);
        return ResponseEntity.ok().body(new CommonResponseDto("회원가입이 완료되었습니다.", HttpStatus.OK.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto,
                                                   HttpServletResponse httpServletResponse) {
        userService.login(userLoginRequestDto);
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userLoginRequestDto.getUsername()));
        return ResponseEntity.ok().body(new CommonResponseDto("로그인이 완료되었습니다.", HttpStatus.OK.value()));
    }
}
