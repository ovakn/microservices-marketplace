package org.example.productService.DTOs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse implements Serializable {
    Long id;
    String name;
    Double price;
    Integer stock;
    Double averageRating;
    List<ReviewResponse> reviews;
}