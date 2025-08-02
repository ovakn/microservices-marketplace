package org.example.orderService.feign;

import org.example.orderService.DTOs.ProductQuantityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "http://product-service:8081"
)
public interface ProductServiceClient {
    @GetMapping("/api/v1/products/availability")
    boolean isProductAvailable(@RequestBody List<ProductQuantityRequest> productsList);

    @PutMapping("api/v1/products/reserve")
    boolean reserveProduct(@RequestBody List<ProductQuantityRequest> productsList);
}