package org.example.productService.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productService.DTOs.ProductDTO;
import org.example.productService.entities.Product;
import org.example.productService.mappers.ProductMapper;
import org.example.productService.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
@RequiredArgsConstructor
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductDTO product) {
        return productRepository.save(productMapper.toProduct(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}