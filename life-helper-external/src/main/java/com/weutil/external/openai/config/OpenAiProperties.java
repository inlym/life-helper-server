package com.weutil.external.openai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OpenAI（即 ChatGPT）开发者配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/19
 * @since 2.1.0
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.openai")
@Data
public class OpenAiProperties {
    /** 请求地址前缀部分 */
    private String baseUrl;

    /** 开发者密钥 */
    private String key;
}
