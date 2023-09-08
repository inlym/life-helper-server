package com.inlym.lifehelper.extern.chatgptold.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 代理 HTTP 请求模型
 *
 * <h2>主要用途
 * <p>封装代理 HTTP 请求模型数据对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/25
 * @since 1.9.5
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProxyRequest {
    private String method;

    private String url;

    private Map<String, String> headers;

    private Object data;
}
