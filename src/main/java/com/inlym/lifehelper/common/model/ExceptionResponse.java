package com.inlym.lifehelper.common.model;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * 异常响应
 * <p>
 * [主要用途]
 * 发生异常时，需要给客户端一个标准响应告知错误，该响应内容包含错误码和错误消息。正常情况下，直接返回对应的响应数据，发生异常时，
 * 则返回当前对象。
 * <p>
 * [客户端如何鉴别响应是否正常]
 * 客户端可以通过判断以下布尔值，判断响应是否正常
 * ```javascript
 * if(response.data.errCode) {
 * // 进入此处说明发生异常
 * } else {
 * // 进入此处说明响应正常
 * }
 * ```
 *
 * @author inlym
 * @since 2022-01-18 19:02
 **/
@Data
public class ExceptionResponse {
    private Integer errCode;

    private String errMsg;

    public ExceptionResponse(int errCode, String errMsg) {
        Assert.isTrue(errCode != 0, "错误码不允许为 0");

        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
