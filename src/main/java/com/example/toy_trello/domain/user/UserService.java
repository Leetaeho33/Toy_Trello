package com.example.toy_trello.domain.user;

import com.example.toy_trello.domain.user.dto.*;
import com.example.toy_trello.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupRequestDto userSignupRequestDto) {
        String username = userSignupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(userSignupRequestDto.getPassword());

        // 입력 확인
        if (userSignupRequestDto.getUsername() == null) {
            throw new IllegalArgumentException("username을 입력하세요.");
        } else if (userSignupRequestDto.getPassword() == null) {
            throw new IllegalArgumentException("password를 입력하세요.");
        } else if (userSignupRequestDto.getEmail() == null) {
            throw new IllegalArgumentException("email을 입력하세요.");
        }

        // 중복 닉네임 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 입니다.");
        }

        // 비밀번호 확인이 비밀번호와 일치하는지 확인
        if (!Objects.equals(userSignupRequestDto.getPassword(), userSignupRequestDto.getCheckPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        User user = new User(userSignupRequestDto, encodedPassword);
        userRepository.save(user);
    }

    public void login(UserLoginRequestDto userLoginRequestDto) {
        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();

        // username 검증
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 username의 사용자가 없습니다.")
        );

        // password 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public UserProfileResponseDto getUserProfile(Long userId) {
        // 해당 id의 유저가 존재하는지 검증
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 유저가 없습니다.")
        );
        return new UserProfileResponseDto(user);
    }

    @Transactional
    public UserProfileResponseDto updateUserProfile(Long userId, UserProfileRequestDto userProfileRequestDto, UserDetailsImpl userDetails) {
        // 해당 id의 유저가 존재하는지 검증
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 유저가 없습니다.")
        );

        // 본인 인증
        if (!Objects.equals(user.getUserId(), userDetails.getUser().getUserId())) {
            throw new IllegalArgumentException("본인만 정보 수정 및 탈퇴 가능합니다.");
        }

        User updatedUser = user.update(userProfileRequestDto);
        return new UserProfileResponseDto(updatedUser);
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto updatePasswordRequestDto, UserDetailsImpl userDetails) {
        String password = updatePasswordRequestDto.getPassword();
        String newPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());

        // 해당 id의 유저가 존재하는지 검증
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 유저가 없습니다.")
        );

        // 본인 인증
        if (!Objects.equals(user.getUserId(), userDetails.getUser().getUserId())) {
            throw new IllegalArgumentException("본인만 정보 수정 및 탈퇴 가능합니다.");
        }

        // password 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 확인이 새로운 비밀번호와 일치하는지 확인
        if (!Objects.equals(updatePasswordRequestDto.getNewPassword(), updatePasswordRequestDto.getCheckNewPassword())) {
            throw new IllegalArgumentException("새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        User passwordUpdatedUser = user.updatePassword(newPassword);
        userRepository.save(passwordUpdatedUser);
    }
}
