package com.inlym.lifehelper.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.inlym.lifehelper"))
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("「我的个人助手」项目 API 文档")
            .version("1.0")
            .description("「我的个人助手」项目 API 文档")
            .build();
    }
}
