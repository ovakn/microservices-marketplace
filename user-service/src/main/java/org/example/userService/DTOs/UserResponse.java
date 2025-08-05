package org.example.userService.DTOs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.userService.entities.enums.UserRole;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String name;
    String phoneNumber;
    String email;
    UserRole userRole;
    AddressResponse address;
}