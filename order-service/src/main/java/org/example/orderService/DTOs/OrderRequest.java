package org.example.orderService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @Schema(description = "Идентификатор пользователя")
    @Min(
            value = 0,
            message = "User id must be positive"
    )
    @NotNull
    Long userId;
    @Schema(description = "Список продуктов")
    @NotNull
    List<OrderItemRequest> items;
}