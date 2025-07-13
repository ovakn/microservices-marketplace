package org.example.productService.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productService.DTOs.ProductDTO;
import org.example.productService.entities.Product;
import org.example.productService.mappers.ProductMapper;
import org.example.productService.repositories.ProductRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "products")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    @Cacheable(value = "products")
    public Slice<Product> getProductsSlice(
            Integer limit,
            Integer offset
    ) {
        log.info("getting page {} with size {}", offset, limit);
        return productRepository.findAll(PageRequest.of(offset, limit));
    }

    @Cacheable
    public Product getProductByName(String name) {
        log.info("getting product with name: " + name);
        return productRepository.findByName(name).orElseThrow(
                () -> new IllegalArgumentException("Product with such name does not exist")
        );
    }

    @Cacheable
    public Product getProductById(Long id) {
        log.info("getting product with id: " + id);
        return productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with such id does not exist")
        );
    }

    @CachePut(key = "#product.name")
    public Product createProduct(ProductDTO product) {
        log.info("creating product {}", product.toString());
        return productRepository.save(productMapper.toProduct(product));
    }

    @CacheEvict(key = "#product.name")
    @CachePut(key = "#product.name")
    public Product updateProduct(
            ProductDTO product,
            Long id
    ) {
        if (productRepository.findById(id).isPresent()) {
            Product newProduct = productMapper.toProduct(product);
            newProduct.setId(id);
            log.info("updating product with id {}. New product: {}", id, product);
            productRepository.save(newProduct);
        }
        throw new IllegalArgumentException("Product with such id does not exist");
    }

    @CacheEvict(key = "#product.name")
    public void deleteProduct(Long id) {
        log.info("deleting product with id {}", id);
        productRepository.deleteById(id);
    }
}