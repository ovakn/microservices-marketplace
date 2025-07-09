package org.example.gatewayAPI.filters;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);
            if (token == null) throw new RuntimeException("Missing token");
            return webClientBuilder
                    .build()
                    .get()
                    .uri("http://localhost:8083/auth/validate?token=" + token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValid -> {
                        if (isValid) return chain.filter(exchange);
                        return Mono.error(new RuntimeException("Invalid token"));
                    });
        };
    }

    public static class Config {}
}