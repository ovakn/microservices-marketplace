package org.example.productService.mappers;

import org.example.productService.DTOs.ProductRequest;
import org.example.productService.DTOs.ProductResponse;
import org.example.productService.entities.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void toDTO() {
        Product product = new Product(1L, "test product", 9.99, 10, null, null);
        ProductResponse waitedResponse = new ProductResponse(
                1L,
                "test product",
                9.99,
                10,
                null,
                null
        );
        ProductResponse response = productMapper.toDTO(product);
        assertEquals(waitedResponse, response);
    }

    @Test
    void toProduct() {
        ProductRequest productRequest = new ProductRequest("test product", 9.99, 10);
        Product waitedProduct = new Product(null, "test product", 9.99, 10, null, null);
        Product product = productMapper.toProduct(productRequest);
        assertEquals(waitedProduct, product);
    }
}