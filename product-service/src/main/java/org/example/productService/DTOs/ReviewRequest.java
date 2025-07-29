package org.example.productService.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Schema(description = "Сущность отзыва")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    @Schema(description = "Идентификатор автора отзыва")
    @NotNull
    @Min(
            value = 1,
            message = "UserId must be positive"
    )
    Long userId;
    @Schema(
            description = "Рейтинг",
            example = "5"
    )
    @NotNull
    @Min(
            value = 1,
            message = "Rating must be between 1 and 5"
    )
    @Max(
            value = 5,
            message = "Rating must be between 1 and 5"
    )
    Integer rating;
    @Schema(description = "Необязательный комментарий к отзыву")
    String comment;
}