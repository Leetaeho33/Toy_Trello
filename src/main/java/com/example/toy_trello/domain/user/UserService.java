package com.example.toy_trello.domain.user;

import com.example.toy_trello.domain.user.dto.*;
import com.example.toy_trello.domain.user.exception.*;
import com.example.toy_trello.domain.column.exception.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.example.toy_trello.domain.user.exception.UserErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupRequestDto userSignupRequestDto) {
        String username = userSignupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(userSignupRequestDto.getPassword());

        if (userSignupRequestDto.getUsername() == null) {
            throw new RequiredUsernameException(REQUIRED_USERNAME);
        } else if (userSignupRequestDto.getPassword() == null) {
            throw new RequiredPasswordException(REQUIRED_PASSWORD);
        } else if (userSignupRequestDto.getEmail() == null) {
            throw new RequiredEmailException(REQUIRED_EMAIL);
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
        }

        if (!Objects.equals(userSignupRequestDto.getPassword(), userSignupRequestDto.getCheckPassword())) {
            throw new PasswordConfirmationFailedException(PASSWORD_CONFIRMATION_FAILED);
        }

        User user = new User(userSignupRequestDto, encodedPassword);
        userRepository.save(user);
    }

    public void login(UserLoginRequestDto userLoginRequestDto) {
        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NonUserExistException(NON_USER_EXIST));
        checkPassword(password, user);
    }

    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = checkIsExistUser(userId);
        return new UserProfileResponseDto(user);
    }

    @Transactional
    public UserProfileResponseDto updateUserProfile(Long userId, UserProfileRequestDto userProfileRequestDto, UserDetailsImpl userDetails) {
        User user = checkIsExistUser(userId);
        verifyIsUserSelf(userDetails, user);
        User updatedUser = user.update(userProfileRequestDto);
        return new UserProfileResponseDto(updatedUser);
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto updatePasswordRequestDto, UserDetailsImpl userDetails) {
        String password = updatePasswordRequestDto.getPassword();
        String newPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());

        User user = checkIsExistUser(userId);
        verifyIsUserSelf(userDetails, user);
        checkPassword(password, user);

        if (!Objects.equals(updatePasswordRequestDto.getNewPassword(), updatePasswordRequestDto.getCheckNewPassword())) {
            throw new PasswordConfirmationFailedException(PASSWORD_CONFIRMATION_FAILED);
        }

        User passwordUpdatedUser = user.updatePassword(newPassword);
        userRepository.save(passwordUpdatedUser);
    }

    public void deleteUser(Long userId, DeleteUserRequestDto deleteUserRequestDto, UserDetailsImpl userDetails) {
        String password = deleteUserRequestDto.getPassword();

        User user = checkIsExistUser(userId);
        verifyIsUserSelf(userDetails, user);
        checkPassword(password, user);
        userRepository.delete(user);
    }

    // 유저 존재 여부 검증 메서드
    private User checkIsExistUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NonUserExistException(NON_USER_EXIST));
    }

    // 본인 인증 메서드
    private static void verifyIsUserSelf(UserDetailsImpl userDetails, User user) {

        if (!Objects.equals(user.getUserId(), userDetails.getUser().getUserId())) {
            throw new SelfAuthenticationFailedException(SELF_AUTHENTICATION_FAILED);
        }
    }

    // password 검증 메서드
    private void checkPassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException(INCORRECT_PASSWORD);
        }
    }
}
