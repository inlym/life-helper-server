package com.inlym.lifehelper.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 * @date 2022/11/27
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    /** 错误码 */
    private Integer errorCode;

    /** 错误消息 */
    private String errorMessage;

    public ErrorResponse(int errorCode) {
        this.errorCode = errorCode;

        // 未特别指明，则使用这句默认提示。
        this.errorMessage = "网络异常，请稍后再试！";
    }

    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
