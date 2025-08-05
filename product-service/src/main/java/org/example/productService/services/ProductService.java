package org.example.productService.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productService.DTOs.*;
import org.example.productService.entities.*;
import org.example.productService.exceptions.ProductNotExistsException;
import org.example.productService.mappers.*;
import org.example.productService.repositories.*;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class ProductService {
    ProductRepository productRepository;
    ReviewRepository reviewRepository;
    ProductMapper productMapper;
    ReviewMapper reviewMapper;

    @Cacheable(
            value = "products",
            key = "#limit, #offset"
    )
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

    @Cacheable(
            value = "reviews",
            key = "#productId, #limit, #offset"
    )
    public Slice<ReviewResponse> getProductReviewsSlice(
            Long productId,
            Integer limit,
            Integer offset
    ) {
        log.info("getting reviews about product with id {}, page {} with size {}", productId, offset, limit);
        return reviewRepository.findByProductId(productId, PageRequest.of(offset, limit)).map(reviewMapper::toDTO);
    }

    @Cacheable(
            value = "products",
            key = "#name"
    )
    public ProductResponse getProductByName(String name) {
        log.info("getting product with name: " + name);
        Product product = productRepository.findByName(name).orElseThrow(
                () -> new ProductNotExistsException(name)
        );
        product.setAverageRating(reviewRepository.calculateAverageRating(product.getId()));
        return productMapper.toDTO(product);
    }

    @Cacheable(
            value = "products",
            key = "#id"
    )
    public ProductResponse getProductById(Long id) {
        log.info("getting product with id: " + id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotExistsException(id)
        );
        product.setAverageRating(reviewRepository.calculateAverageRating(product.getId()));
        return productMapper.toDTO(product);
    }

    public Boolean getAvailability(List<ProductQuantityRequest> productsList) {
        for (ProductQuantityRequest product: productsList) {
            if (!checkingAvailability(product.getProductId(), product.getQuantity())) return false;
        }
        return true;
    }

    public Boolean checkingAvailability(Long id, Integer quantity) {
        log.info("checking availability of product with id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotExistsException(id)
        );
        return product.getStock() > quantity;
    }

    @CachePut(
            value = "products",
            key = "#product.name"
    )
    public ProductResponse createProduct(ProductRequest product) {
        log.info("creating product {}", product.toString());
        return productMapper.toDTO(productRepository.save(productMapper.toProduct(product)));
    }

    @CachePut(
            value = "reviews",
            key = "#productId"
    )
    @Transactional
    public ReviewResponse createReview(Long productId, ReviewRequest reviewRequest) {
        log.info("creating new review ({}) about a product with id {}", reviewRequest.toString(), productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotExistsException(productId)
        );
        Review review = reviewMapper.toReview(reviewRequest);
        review.setProduct(product);
        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @CachePut(
            value = "products",
            key = "#id"
    )
    @Transactional
    public ProductResponse updateProduct(
            ProductRequest product,
            Long id
    ) {
        Product oldProduct = productRepository.findById(id).orElseThrow(
                () -> new ProductNotExistsException(id)
        );
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setStock(product.getStock());
        log.info("updating product with id {}. New product info: {}", id, product);
        return productMapper.toDTO(oldProduct);
    }

    @Transactional
    public Boolean reserveProduct(List<ProductQuantityRequest> productsList) {
        for (ProductQuantityRequest productRequest: productsList) {
            log.info("reserving product with id: {}, with quantity {}", productRequest.getProductId(), productRequest.getQuantity());
            Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(
                    () -> new ProductNotExistsException(productRequest.getProductId())
            );
            product.setStock(product.getStock() - productRequest.getQuantity());
        }
        return true;
    }

    @CacheEvict(
            value = "products",
            key = "#id"
    )
    public void deleteProduct(Long id) {
        log.info("deleting product with id {}", id);
        productRepository.deleteById(id);
    }
}