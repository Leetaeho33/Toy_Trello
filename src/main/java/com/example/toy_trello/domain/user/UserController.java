package com.example.toy_trello.domain.user;

import com.example.toy_trello.domain.user.dto.*;
import com.example.toy_trello.global.dto.CommonResponseDto;
import com.example.toy_trello.global.jwt.JwtUtil;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserProfile(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> updateUserProfile(@PathVariable Long userId,
                                                               @Valid @RequestBody UserProfileRequestDto userProfileRequestDto,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(userService.updateUserProfile(userId, userProfileRequestDto, userDetails));
    }

    @PatchMapping("/{userId}/passwords")
    public ResponseEntity<CommonResponseDto> updatePassword(@PathVariable Long userId,
                                                            @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(userId, updatePasswordRequestDto, userDetails);
        return ResponseEntity.ok().body(new CommonResponseDto("비밀번호가 변경되었습니다.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> deleteUser(@PathVariable Long userId,
                                                        @RequestBody DeleteUserRequestDto deleteUserRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userId, deleteUserRequestDto, userDetails);
        return ResponseEntity.ok().body(new CommonResponseDto("회원 탈퇴에 성공하였습니다.", HttpStatus.OK.value()));
    }
}
