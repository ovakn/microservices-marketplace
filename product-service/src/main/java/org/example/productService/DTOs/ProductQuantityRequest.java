package org.example.productService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Schema(description = "Сущность для проверки количества товара и его бронирования")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductQuantityRequest {
    @Min(
            value = 1,
            message = "Product id must be positive"
    )
    @NotNull
    Long productId;
    @Schema(description = "Количество необходимого товара")
    @Min(
            value = 1,
            message = "Quantity must be positive"
    )
    @NotNull
    Integer quantity;
}