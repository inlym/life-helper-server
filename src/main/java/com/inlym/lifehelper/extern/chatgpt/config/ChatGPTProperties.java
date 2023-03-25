package com.inlym.lifehelper.extern.chatgpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ChatGPT 配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@Component
@ConfigurationProperties(prefix = "lifehelper.chatgpt")
@Data
public class ChatGPTProperties {
    /**
     * 开发者密钥列表
     *
     * <h3>为什么要使用一个密钥列表而不是单个密钥？
     * <p>密钥经常被封，避免出现该情况时整个服务不可用。
     */
    private String[] keys;

    /**
     * 代理 HTTP 请求地址
     *
     * <h2>说明
     * <p>将所有发往 ChatGPT 的 HTTP 请求通过该代理进行中转。
     */
    private String proxyUrl;
}
