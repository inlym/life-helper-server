package com.inlym.lifehelper.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2021-12-16
 * @since 1.0.0
 */
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
            .title("「小鸣助手」项目 API 文档")
            .version("1.0")
            .description("「小鸣助手」项目 API 文档")
            .build();
    }
}
