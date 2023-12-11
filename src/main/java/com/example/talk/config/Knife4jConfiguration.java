package com.example.talk.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/11 11:01
 */
@Configuration
public class Knife4jConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("talk-project rest api document")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("iumyxF")
                                .email("https://google.com")
                                .url("https://google.com")
                        )
                        .description("api document")
                        .termsOfService("https://google.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://google.com")
                        )
                );
    }

    @Bean
    public GroupedOpenApi allApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.example.talk"};
        return GroupedOpenApi.builder().group("all")
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> operation)
                .packagesToScan(packagedToMatch).build();
    }
}
