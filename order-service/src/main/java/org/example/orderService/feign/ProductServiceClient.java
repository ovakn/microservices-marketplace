package org.example.orderService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "product-service",
        url = "http://product-service:8081"
)
public interface ProductServiceClient {
    @GetMapping("/api/v1/products/{productId}/availability")
    boolean isProductAvailable(
            @PathVariable("productId") Long id,
            @RequestParam int quantity
    );

    @PutMapping("api/v1/products/{productId}/reserve")
    void reserveProduct(
            @PathVariable("productId") Long id,
            @RequestParam int quantity
    );
}