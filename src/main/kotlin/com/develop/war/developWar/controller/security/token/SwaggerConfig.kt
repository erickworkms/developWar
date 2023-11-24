package com.develop.war.developWar.controller.security.token

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun detalheApi(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title("API TESTE")
                .description("TESTE")
                .contact(
                    Contact().name("Teste")
                        .email("teste@gmail.com")
                )
                .version("1.0.0")
        )
    }
}