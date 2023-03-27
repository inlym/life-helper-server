package com.inlym.lifehelper.extern.chatgpt.pojo;

import lombok.Data;

/**
 * 响应数据错误字段
 *
 * <h2>主要用途
 * <p>当相应数据包含 "error" 字段时，表示请求错误。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Data
public class ApiError {
    /** 错误消息 */
    private String message;

    /** 错误类型 */
    private Integer type;
}
