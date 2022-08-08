package com.inlym.lifehelper.common.model;

import lombok.Data;
import org.springframework.util.Assert;

/**
 * 通用异常响应
 *
 * <h2>主要用途
 * <p>发生异常时，需要给客户端一个标准响应告知错误，该响应内容包含错误码和错误消息。正常情况下，直接返回对应的响应数据，发生异常时，
 * 则返回当前对象。
 *
 * <h2>客户端如何鉴别响应是否正常
 * <p>客户端可以通过判断以下布尔值，判断响应是否正常
 *
 * <h2>使用说明
 * <pre class="code">
 * if(response.data.errCode) {
 *   // 进入此处说明发生异常
 * } else {
 *   // 进入此处说明响应正常
 * }
 * </pre>
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-18
 * @since 1.0.0
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

    /**
     * 一般通用性错误提示，用于服务端错误
     *
     * @since 1.3.0
     */
    public static ExceptionResponse forServerError() {
        return new ExceptionResponse(50000, "网络故障，请稍后再试！");
    }
}
