package org.example.productService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Schema(description = "Сущность продукта")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @Schema(
            description = "Название продукта",
            example = "Нож кухонный"
    )
    @NotNull
    String name;
    @Schema(
            description = "Стоимость продукта",
            example = "1500"
    )
    @Min(
            value = 0,
            message = "Product price must be positive"
    )
    @NotNull
    Double price;
    @Schema(
            description = "Количество продукта",
            example = "15"
    )
    @NotNull
    Integer stock;
}