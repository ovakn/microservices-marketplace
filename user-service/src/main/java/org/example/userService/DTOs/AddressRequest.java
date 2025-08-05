package org.example.userService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Schema(description = "Сущность адреса пользователя в виде DTO")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {
    @Schema(description = "Страна пользователя")
    @NotNull
    String country;
    @Schema(description = "Город пользователя")
    @NotNull
    String city;
    @Schema(description = "Улица пользователя")
    @NotNull
    String street;
    @Schema(description = "Почтовый индекс пользователя")
    @NotNull
    String postCode;
}