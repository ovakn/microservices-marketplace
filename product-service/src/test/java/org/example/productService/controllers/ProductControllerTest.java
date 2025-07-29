package org.example.productService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productService.DTOs.ProductRequest;
import org.example.productService.DTOs.ProductResponse;
import org.example.productService.DTOs.ReviewRequest;
import org.example.productService.DTOs.ReviewResponse;
import org.example.productService.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockitoBean
    private ProductService productService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getProductById() throws Exception {
        ProductResponse productResponse = new ProductResponse(
                1L,
                "test product",
                9.99,
                10,
                4.9,
                List.of()
        );
        when(productService.getProductById(1L)).thenReturn(productResponse);
        mockMvc
                .perform(get("/api/v1/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test product"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.averageRating").value(4.9));
        verify(productService, times(1)).getProductById(any());
    }

    @Test
    void getProductById_returnsNotFound() throws Exception {
        when(productService.getProductById(any())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(get("/api/v1/products/{id}", 1L)).andExpect(status().isNotFound());
        verify(productService, times(1)).getProductById(any());
    }

    @Test
    void getProductByName() throws Exception {
        ProductResponse productResponse = new ProductResponse(
                1L,
                "test product",
                9.99,
                10,
                4.9,
                List.of()
        );
        when(productService.getProductByName(any())).thenReturn(productResponse);
        mockMvc
                .perform(get("/api/v1/products/getByName/{name}", "test product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test product"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.averageRating").value(4.9));
        verify(productService, times(1)).getProductByName(any());
    }

    @Test
    void getProductByName_returnsNotFound() throws Exception {
        when(productService.getProductByName(any())).thenThrow(IllegalArgumentException.class);
        mockMvc
                .perform(get("/api/v1/products/getByName/{name}", "test product"))
                .andExpect(status().isNotFound());
        verify(productService, times(1)).getProductByName(any());
    }

    @Test
    void isAvailable() throws Exception {
        when(productService.getAvailability(1L, 3)).thenReturn(true);
        mockMvc
                .perform(get("/api/v1/products/{id}/availability", 1L).param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));
        verify(productService, times(1)).getAvailability(1L, 3);
    }

    @Test
    void createProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("test product", 9.99, 10);
        String jsonProduct = objectMapper.writeValueAsString(productRequest);
        ProductResponse productResponse = new ProductResponse(
                1L,
                "test product",
                9.99,
                10,
                4.9,
                List.of()
        );
        when(productService.createProduct(any())).thenReturn(productResponse);
        mockMvc
                .perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test product"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.stock").value(10))
                .andExpect(jsonPath("$.averageRating").value(4.9));
        verify(productService, times(1)).createProduct(any());
    }

    @Test
    void createProduct_returnsBadRequest() throws Exception {
        ProductRequest productRequest = new ProductRequest("test product", -2.5, 10);
        String jsonProduct = objectMapper.writeValueAsString(productRequest);
        mockMvc
                .perform(post("/api/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isBadRequest());
        verify(productService, times(0)).createProduct(any());
    }

    @Test
    void createReview() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest(1L, 4, "comment");
        String jsonReview = objectMapper.writeValueAsString(reviewRequest);
        ReviewResponse reviewResponse = new ReviewResponse(1L, 1L, 4, "comment", null);
        when(productService.createReview(any(), any())).thenReturn(reviewResponse);
        mockMvc
                .perform(post("/api/v1/products/{id}/createReview", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReview))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("comment"));
        verify(productService, times(1)).createReview(any(), any());
    }

    @Test
    void createReview_returnsBadRequest() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest(0L, 6, "comment");
        String jsonReview = objectMapper.writeValueAsString(reviewRequest);
        mockMvc
                .perform(post("/api/v1/products/{id}/createReview", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReview))
                .andExpect(status().isBadRequest());
        verify(productService, times(0)).createReview(any(), any());
    }

    @Test
    void updateProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("test product", 9.99, 10);
        String jsonProduct = objectMapper.writeValueAsString(productRequest);
        ProductResponse productResponse = new ProductResponse(
                1L,
                "test product",
                9.99,
                10,
                4.9,
                List.of()
        );
        when(productService.updateProduct(any(), any())).thenReturn(productResponse);
        mockMvc
                .perform(put("/api/v1/products/{id}/update", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test product"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.stock").value(10))
                .andExpect(jsonPath("$.averageRating").value(4.9));
        verify(productService, times(1)).updateProduct(any(), any());
    }

    @Test
    void updateProduct_returnsBadRequest() throws Exception {
        ProductRequest productRequest = new ProductRequest("test product", -2.5, 10);
        String jsonProduct = objectMapper.writeValueAsString(productRequest);
        mockMvc
                .perform(put("/api/v1/products/{id}/update", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isBadRequest());
        verify(productService, times(0)).updateProduct(any(), any());
    }
}