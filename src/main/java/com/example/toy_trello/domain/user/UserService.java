package com.example.toy_trello.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
