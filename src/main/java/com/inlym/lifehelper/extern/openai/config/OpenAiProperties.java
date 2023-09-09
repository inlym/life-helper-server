package com.inlym.lifehelper.extern.openai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OpenAI（即 ChatGPT）开发者配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/9
 * @since 2.0.3
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.openai")
@Data
public class OpenAiProperties {
    /**
     * 代理 HTTP 请求地址
     * <p>
     * <h2>说明
     * <p>将所有发往 ChatGPT 的 HTTP 请求通过该代理进行中转。
     */
    private String proxyUrl;

    /**
     * 开发者密钥列表
     * <p>
     * <h3>为什么要使用一个密钥列表而不是单个密钥？
     * <p>密钥经常被封，避免出现该情况时整个服务不可用。
     */
    private String[] keys;
}
