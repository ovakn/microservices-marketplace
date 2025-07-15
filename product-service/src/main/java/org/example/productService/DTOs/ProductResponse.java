package org.example.productService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse implements Serializable {
    Long id;
    String name;
    Double price;
    Integer stock;
    Double averageRating;
    List<ReviewResponse> reviews;
}