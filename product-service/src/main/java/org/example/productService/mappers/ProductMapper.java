package org.example.productService.mappers;

import org.example.productService.DTOs.ProductDTO;
import org.example.productService.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }

    public Product toProduct(ProductDTO productDTO) {
        return new Product(
                null,
                productDTO.name(),
                productDTO.price(),
                productDTO.stock()
        );
    }
}