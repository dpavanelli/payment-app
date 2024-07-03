package com.pavanelli.payments.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment API")
                        .description("API for managing payments")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Payment API Documentation")
                        .url("http://localhost:8080"));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
