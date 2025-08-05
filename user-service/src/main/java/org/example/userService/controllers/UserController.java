package org.example.userService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.userService.DTOs.UserRequest;
import org.example.userService.DTOs.UserResponse;
import org.example.userService.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(
        name = "Контроллер пользователей",
        description = "Контроллер для взаимодействия с базой данных продуктов"
)
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class UserController {
    UserService userService;

    @Operation(
            summary = "Авторизация пользователя",
            description = "Проверяет соответствие переданных данных с теми, что хранятся в базе данных"
    )
    @GetMapping("/authorise")
    public ResponseEntity<UserResponse> authorise(
            @Parameter(description = "Электронная почта пользователя", required = true) String email,
            @Parameter(description = "Пароль пользователя", required = true) String password
    ) {
        return new ResponseEntity<>(userService.authorise(email, password), HttpStatus.OK);
    }

    @Operation(
            summary = "Проверка существования пользователя",
            description = "Проверяет, есть ли пользователь с переданным идентификатором в базе данных"
    )
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> doesExistById(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        return new ResponseEntity<>(userService.checkUserExisting(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание нового пользователя",
            description = "Создаёт нового пользователя и возвращает его в виде DTO"
    )
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "Новый пользователь", required = true) @Validated @RequestBody UserRequest userRequest
    ) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление пользователя",
            description = "Обновляет информацию в базе данных о конкретном пользователе"
    )
    @PutMapping("/{id}/update")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "Идентификатор пользователя", required = true)
            @PathVariable("id")
            @Min(1)
            Long id,
            @Parameter(description = "Обновлённый пользователь", required = true) @Validated @RequestBody UserRequest userRequest
    ) {
        return new ResponseEntity<>(userService.updateUserInfo(id, userRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Смена пароля пользователя",
            description = "Возвращает страницу продуктов заданного размера"
    )
    @PutMapping("/{id}/changePassword")
    public ResponseEntity<Void> changeUserPassword(
            @Parameter(description = "Идентификатор пользователя", required = true)
            @PathVariable("id")
            @Min(1)
            Long id,
            @Parameter(description = "Новый пароль", required = true) String newPassword
    ) {
        userService.changeUserPassword(id, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}