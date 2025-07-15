package org.example.productService.mappers;

import org.example.productService.DTOs.ProductRequest;
import org.example.productService.DTOs.ProductResponse;
import org.example.productService.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toDTO(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                null,
                null
        );
    }

    public Product toProduct(ProductRequest productRequest) {
        return new Product(
                null,
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getStock(),
                null,
                null
        );
    }
}