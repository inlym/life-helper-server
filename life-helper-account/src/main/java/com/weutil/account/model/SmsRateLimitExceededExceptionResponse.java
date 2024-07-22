package com.weutil.account.model;

import com.weutil.common.model.ErrorResponse;
import lombok.Getter;

/**
 * 类名 * 发生“短信发送速率超出限制异常”时的响应数据称
 *
 * <h2>说明
 * <p>说明文本内容
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Getter
public class SmsRateLimitExceededExceptionResponse extends ErrorResponse {
    /**
     * 剩余的等待秒数
     *
     * <h3>说明
     * <p>即下一次可以发送短信距此刻的秒数
     */
    private final Long remainingSeconds;

    public SmsRateLimitExceededExceptionResponse(int errorCode, String errorMessage, long remainingSeconds) {
        super(errorCode, errorMessage);

        this.remainingSeconds = remainingSeconds;
    }
}
