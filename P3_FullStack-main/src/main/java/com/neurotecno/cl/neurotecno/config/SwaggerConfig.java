package com.neurotecno.cl.neurotecno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Api administración de Atenciones")
            .version("2.0")
            .description("El objetivo de esta API es gestionar horas de atencion de neurotecno")
        );
    }
}
