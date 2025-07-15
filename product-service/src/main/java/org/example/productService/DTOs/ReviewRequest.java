package org.example.productService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Сущность отзыва")
@Data
@AllArgsConstructor
public class ReviewRequest {
    @Schema(description = "Идентификатор автора отзыва")
    @NotNull
    Long userId;
    @Schema(
            description = "Рейтинг",
            example = "5"
    )
    @NotNull
    @Min(value = 1,
            message = "Rating must be between 1 and 5")
    @Max(value = 5,
            message = "Rating must be between 1 and 5")
    Integer rating;
    @Schema(description = "Необязательный комментарий к отзыву")
    String comment;
}