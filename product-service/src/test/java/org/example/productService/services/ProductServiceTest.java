package org.example.productService.services;

import org.example.productService.DTOs.ProductQuantityRequest;
import org.example.productService.DTOs.ProductRequest;
import org.example.productService.DTOs.ReviewRequest;
import org.example.productService.entities.Product;
import org.example.productService.entities.Review;
import org.example.productService.exceptions.ProductNotExistsException;
import org.example.productService.mappers.ProductMapper;
import org.example.productService.mappers.ReviewMapper;
import org.example.productService.repositories.ProductRepository;
import org.example.productService.repositories.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ReviewMapper reviewMapper;

    @Test
    void getProductByName() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findByName("test product")).thenReturn(Optional.of(product));
        assertEquals(productMapper.toDTO(product), productService.getProductByName("test product"));
        verify(productRepository, times(1)).findByName(any());
    }

    @Test
    void getProductByName_throwsException() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findByName("test product")).thenReturn(Optional.of(product));
        assertThrows(ProductNotExistsException.class, () -> productService.getProductByName("some wrong product"));
        verify(productRepository, times(1)).findByName(any());
    }

    @Test
    void getProductById() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals(productMapper.toDTO(product), productService.getProductById(1L));
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void getProductById_throwsException() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertThrows(ProductNotExistsException.class, () -> productService.getProductById(2L));
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void getAvailability() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals(true, productService.getAvailability(List.of(new ProductQuantityRequest(1L, 2))));
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void getAvailability_throwsException() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertThrows(ProductNotExistsException.class,
                () -> productService.getAvailability(List.of(new ProductQuantityRequest(2L, 2))));
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void createProduct() {
        ProductRequest productRequest = new ProductRequest("test product", 15.99, 50);
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.save(any())).thenReturn(product);
        when(productMapper.toProduct(productRequest)).thenReturn(new Product(
                null,
                "test product",
                15.99,
                50,
                null,
                null)
        );
        assertEquals(productMapper.toDTO(product), productService.createProduct(productRequest));
        verify(productRepository, times(1)).save(any());
        verify(productMapper, times(1)).toProduct(any());
    }

    @Test
    void createReview() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        ReviewRequest reviewRequest = new ReviewRequest(12L, 3, "test comment");
        Review review = new Review(1L, product, 12L, 3, "test comment", null);
        when(reviewMapper.toReview(any())).thenReturn(new Review(
                null,
                null,
                12L,
                3,
                "test comment",
                null)
        );
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any())).thenReturn(review);
        assertEquals(reviewMapper.toDTO(review), productService.createReview(1L, reviewRequest));
        verify(productRepository, times(1)).findById(any());
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).toReview(any());
    }

    @Test
    void createReview_throwsException() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        ReviewRequest reviewRequest = new ReviewRequest(12L, 3, "test comment");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertThrows(ProductNotExistsException.class, () -> productService.createReview(2L, reviewRequest));
        verify(reviewRepository, times(0)).save(any());
        verify(reviewMapper, times(0)).toReview(any());
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void updateProduct() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductRequest productRequest = new ProductRequest("new product", 9.99, 15);
        Product newProduct = new Product(1L, "new product", 9.99, 15, null, List.of());
        assertEquals(productMapper.toDTO(newProduct), productService.updateProduct(productRequest, 1L));
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void updateProduct_throwsException() {
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductRequest productRequest = new ProductRequest("new product", 9.99, 15);
        assertThrows(ProductNotExistsException.class, () -> productService.updateProduct(productRequest, 2L));
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void reserveProduct() {
        List<ProductQuantityRequest> productsList = List.of(new ProductQuantityRequest(1L, 3));
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.reserveProduct(productsList);
        assertEquals(47, product.getStock());
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    void reserveProduct_throwsException() {
        List<ProductQuantityRequest> productsList = List.of(new ProductQuantityRequest(2L, 3));
        Product product = new Product(1L, "test product", 15.99, 50, null, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertThrows(ProductNotExistsException.class, () -> productService.reserveProduct(productsList));
        verify(productRepository, times(0)).findById(1L);
        verify(productRepository, times(1)).findById(2L);
    }
}