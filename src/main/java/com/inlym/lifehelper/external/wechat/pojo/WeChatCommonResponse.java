package com.inlym.lifehelper.external.wechat.pojo;

/**
 * 微信 HTTP 请求统一响应数据格式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
public interface WeChatCommonResponse {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    Integer getErrorCode();

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    String getErrorMessage();
}
