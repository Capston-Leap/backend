package com.dash.leap.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static io.swagger.v3.oas.models.security.SecurityScheme.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Leap API")
                        .description("Leap API 문서입니다.")
                        .version("v1.0"))
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8080").description("Local Server URL"),
                        new Server().url("http://ceprj.gachon.ac.kr:60013").description("Gachon Server URL")
                ))
                .addSecurityItem(new SecurityRequirement().addList("Auth"))
                .components(attachBearerAuthScheme());
    }

    private Components attachBearerAuthScheme() {
        return new Components().addSecuritySchemes("Auth",
                new SecurityScheme()
                        .type(Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(In.HEADER)
                        .name("Authorization")
                        .description("Enter your JWT token"));
    }
}
