package com.weutil.sms.exception;

import lombok.Getter;

/**
 * 短信发送速率超出限制异常
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Getter
public class SmsRateLimitExceededException extends RuntimeException {
    /**
     * 剩余的等待秒数
     *
     * <h3>说明
     * <p>即下一次可以发送短信距此刻的秒数
     */
    private final Long remainingSeconds;

    public SmsRateLimitExceededException(long remainingSeconds) {
        super();

        this.remainingSeconds = remainingSeconds;
    }
}
