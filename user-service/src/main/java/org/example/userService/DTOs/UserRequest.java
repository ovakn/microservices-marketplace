package org.example.userService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.userService.entities.enums.UserRole;

@Schema(description = "Сущность пользователя в виде DTO")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @Schema(description = "Имя пользователя")
    @NotNull
    String name;
    @Schema(description = "Номер телефона пользователя")
    @NotNull
    String phoneNumber;
    @Schema(description = "Адрес электронной почты пользователя")
    @NotNull
    String email;
    @Schema(description = "Пароль пользователя")
    @NotNull
    String password;
    @Schema(description = "Роль пользователя")
    @NotNull
    UserRole userRole;
    @Schema(description = "Адрес пользователя")
    @NotNull
    AddressRequest address;
}