package org.example.gatewayAPI.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .uri("http://product-service:8081")
                )
                .route("order-service", r -> r
                        .path("/api/v1/orders/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("orderCB")
                                        .setFallbackUri("forward:/fallback/order")
                                )
                        )
                        .uri("http://order-service:8082")
                )
                .build();
    }
}