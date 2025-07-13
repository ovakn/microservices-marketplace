package org.example.productService.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    String name;
    @Min(value = 0,
            message = "Product price must be positive")
    @NotNull
    Double price;
    @NotNull
    Integer stock;
}