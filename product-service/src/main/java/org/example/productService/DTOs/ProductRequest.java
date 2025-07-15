package org.example.productService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Сущность продукта")
@Data
@AllArgsConstructor
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
    @Min(value = 0,
            message = "Product price must be positive")
    @NotNull
    Double price;
    @Schema(
            description = "Количество продукта",
            example = "15"
    )
    @NotNull
    Integer stock;
}