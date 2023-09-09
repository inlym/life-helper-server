package com.inlym.lifehelper.extern.openai.service;

import com.inlym.lifehelper.extern.openai.config.OpenAiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ChatGPT 模块配置信息服务
 * <p>
 * <h2>主要用途
 * <p>将配置信息相关的内容封装在当前服务类中。
 * <p>
 * <h2>调用链说明
 * <p>当前服务类只允许在 {@linkplain OpenAiHttpService} 中调用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/9/9
 * @since 2.0.3
 **/
@Service
@RequiredArgsConstructor
public class OpenAiProfileService {
    private final OpenAiProperties properties;

    private int counter = 0;

    /**
     * 获取请求地址前缀部分
     *
     * @date 2023/9/9
     * @since 2.0.3
     */
    public String getBaseUrl() {
        return properties.getProxyUrl();
    }

    /**
     * 获取一个开发者密钥
     *
     * @date 2023/9/9
     * @since 2.0.3
     */
    public String getKey() {
        String[] keys = properties.getKeys();
        String key = keys[counter % keys.length];
        counter++;

        return key;
    }
}
