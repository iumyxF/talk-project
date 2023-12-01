package com.example.talk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author iumyxF
 * @description: Knife4j配置文件
 * <a href="http://localhost:8102/api/doc.html#/home"/>
 * @date 2023/12/1 14:16
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("talk project rest ful apis")
                        .termsOfServiceUrl("https://www.baidu.com/")
                        .contact(new Contact("name", "www.baidu.com", "xxx@google.com"))
                        .version("1.0.0")
                        .build())
                //分组名称
                .groupName("all api")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.talk.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
