package org.example.userService.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.userService.DTOs.UserRequest;
import org.example.userService.DTOs.UserResponse;
import org.example.userService.entities.User;
import org.example.userService.exceptions.NotCorrectPasswordException;
import org.example.userService.exceptions.UserNotExistsException;
import org.example.userService.mappers.UserMapper;
import org.example.userService.repositories.UserRepository;
import org.example.userService.utils.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse authorise(String email, String password) {
        log.info("trying to authorise user with email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotExistsException(email)
        );
        if (PasswordUtil.checkPassword(password, user.getHashPassword())) {
            return userMapper.toDTO(user);
        } else {
            throw new NotCorrectPasswordException();
        }
    }

    public Boolean checkUserExisting(Long id) {
        log.info("checking existing of user with id: {}", id);
        return userRepository.existsById(id);
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("trying to create new user");
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserNotExistsException(userRequest.getEmail());
        }
        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new UserNotExistsException(userRequest.getPhoneNumber());
        }
        return userMapper.toDTO(userRepository.save(userMapper.toUser(userRequest)));
    }

    @Transactional
    public UserResponse updateUserInfo(Long id, UserRequest userRequest) {
        log.info("updating user with id: {}", id);
        User oldUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotExistsException(id)
        );
        User newUser = userMapper.toUser(userRequest);
        oldUser.setName(newUser.getName());
        oldUser.setPhoneNumber(newUser.getPhoneNumber());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setUserRole(newUser.getUserRole());
        oldUser.setAddress(newUser.getAddress());
        return userMapper.toDTO(userRepository.save(oldUser));
    }

    public void changeUserPassword(Long id, String newPassword) {
        log.info("trying to change password for user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotExistsException(id)
        );
        user.setHashPassword(PasswordUtil.hashPassword(newPassword));
    }
}