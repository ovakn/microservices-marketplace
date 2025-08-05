package org.example.orderService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "http://user-service:8083"
)
public interface UserServiceClient {
    @GetMapping("/api/v1/users/{id}/exists")
    Boolean doesExistById(@PathVariable("id") Long id);
}