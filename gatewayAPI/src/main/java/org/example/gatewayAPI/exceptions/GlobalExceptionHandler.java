package org.example.gatewayAPI.exceptions;

import lombok.NonNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    @NonNull
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorMessage = "{\"error\": \""
                + ex.getMessage().replace("\"", "'")
                + "\"}";
        return exchange
                .getResponse()
                .writeWith(Mono.just(exchange
                        .getResponse()
                        .bufferFactory()
                        .wrap(errorMessage.getBytes())));
    }
}