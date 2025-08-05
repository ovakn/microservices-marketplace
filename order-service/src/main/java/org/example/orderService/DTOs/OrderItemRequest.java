package org.example.orderService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    @Schema(description = "Идентификатор продукта")
    @Min(
            value = 0,
            message = "User id must be positive"
    )
    @NotNull
    Long productId;
    @Schema(description = "Количество единиц продукта")
    @Min(
            value = 1,
            message = "You can't order less 1 product"
    )
    @NotNull
    Integer quantity;
}