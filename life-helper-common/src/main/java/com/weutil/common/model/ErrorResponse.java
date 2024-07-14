package com.weutil.common.model;

import lombok.Data;

/**
 * 错误响应
 *
 * <h2>主要用途
 * <p>当出现异常情况无法返回正常响应时，返回这个错误响应。
 *
 * <h2>错误码说明
 * <li> [0]:请求成功，非错误响应。
 * <li> [1~9999]:系统级错误，客户端需要在全局拦截器中处理。
 * <li> [ >9999 ]:业务级错误，在各个业务代码中单独处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Data
public class ErrorResponse {
    /** 错误码 */
    private Integer errorCode;

    /** 错误消息 */
    private String errorMessage;

    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
