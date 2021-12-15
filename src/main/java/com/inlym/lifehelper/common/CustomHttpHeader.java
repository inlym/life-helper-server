package com.inlym.lifehelper.common;

/**
 * 自定义的 HTTP 请求头
 */
public interface CustomHttpHeader {
    /**
     * 唯一请求 ID，用作全链路追踪 ID
     */
    String REQUEST_ID = "X-Ca-Request-Id";
}
