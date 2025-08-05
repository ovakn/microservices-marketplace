package org.example.userService.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.userService.DTOs.UserRequest;
import org.example.userService.DTOs.UserResponse;
import org.example.userService.entities.User;
import org.example.userService.utils.PasswordUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class UserMapper {
    AddressMapper addressMapper;

    public UserResponse toDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getUserRole(),
                addressMapper.toDTO(user.getAddress())
        );
    }

    public User toUser(UserRequest userRequest) {
        return new User(
                null,
                userRequest.getName(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail(),
                PasswordUtil.hashPassword(userRequest.getPassword()),
                userRequest.getUserRole(),
                addressMapper.toAddress(userRequest.getAddress())
        );
    }
}