package org.example.productService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponse implements Serializable {
    Long id;
    Long userId;
    Integer rating;
    String comment;
    LocalDateTime createdAt;
}