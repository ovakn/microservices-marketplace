package org.example.userService.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://localhost:8083")
                        )
                )
                .info(
                        new Info()
                                .title("User Service API")
                                .description("API микросервиса пользователей")
                                .version("1.0.0")
                );
    }
}