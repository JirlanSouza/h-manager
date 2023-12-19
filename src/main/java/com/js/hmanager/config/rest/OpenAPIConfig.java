package com.js.hmanager.config.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI penAPIDefinition() {
        return new OpenAPI()
                .info(new Info().title("Hmanager - Gestão de hoteis")
                        .description("Sistema para gestão de hoteis")
                        .version("v0.0.1"));
    }
}
