package org.example.productService.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productService.DTOs.*;
import org.example.productService.entities.*;
import org.example.productService.mappers.*;
import org.example.productService.repositories.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ReviewRepository reviewRepository;
    ProductMapper productMapper;
    ReviewMapper reviewMapper;

    @Cacheable("products")
    public Slice<ProductResponse> getProductsSlice(
            Integer limit,
            Integer offset
    ) {
        log.info("getting products page {} with size {}", offset, limit);
        Slice<Product> products = productRepository.findAll(PageRequest.of(offset, limit));
        return products.map(product -> {
            ProductResponse productResponse = productMapper.toDTO(product);
            productResponse.setAverageRating(reviewRepository.calculateAverageRating(product.getId()));
            productResponse.setReviews(product
                    .getReviews().stream()
                    .map(reviewMapper::toDTO).collect(Collectors.toList())
            );
            return productResponse;
        });
    }

    @Cacheable("reviews")
    public Slice<ReviewResponse> getProductReviewsSlice(
            Long productId,
            Integer limit,
            Integer offset
    ) {
        log.info("getting reviews about product with id {}, page {} with size {}", productId, offset, limit);
        return reviewRepository.findByProductId(productId, PageRequest.of(offset, limit)).map(reviewMapper::toDTO);
    }

    @Cacheable("products")
    public ProductResponse getProductByName(String name) {
        log.info("getting product with name: " + name);
        Product product = productRepository.findByName(name).orElseThrow(
                () -> new IllegalArgumentException("Product with such name does not exist")
        );
        product.setAverageRating(reviewRepository.calculateAverageRating(product.getId()));
        return productMapper.toDTO(product);
    }

    @Cacheable("products")
    public ProductResponse getProductById(Long id) {
        log.info("getting product with id: " + id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with such id does not exist")
        );
        product.setAverageRating(reviewRepository.calculateAverageRating(product.getId()));
        return productMapper.toDTO(product);
    }

    @CachePut(
            value = "products",
            key = "#product.name"
    )
    public ProductResponse createProduct(ProductRequest product) {
        log.info("creating product {}", product.toString());
        return productMapper.toDTO(productRepository.save(productMapper.toProduct(product)));
    }

    @Transactional
    @CachePut("reviews")
    public ReviewResponse createReview(Long productId, ReviewRequest reviewRequest) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product with such id does not exist")
        );
        Review review = reviewMapper.toReview(reviewRequest);
        review.setProduct(product);
        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @CachePut(
            value = "products",
            key = "#product.name"
    )
    @Transactional
    public ProductResponse updateProduct(
            ProductRequest product,
            Long id
    ) {
        Product oldProduct = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with such id does not exist")
        );
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setStock(product.getStock());
        log.info("updating product with id {}. New product info: {}", id, product);
        return productMapper.toDTO(oldProduct);
    }

    @CacheEvict(
            value = "products",
            key = "#product.name"
    )
    public void deleteProduct(Long id) {
        log.info("deleting product with id {}", id);
        productRepository.deleteById(id);
    }
}