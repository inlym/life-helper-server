package com.weutil.aliyun.captcha.exception;

/**
 * 阿里云验证码校验不通过异常
 *
 * <h3>客户端处理策略
 * <p>在回调函数 {@code captchaVerifyCallback} 的参数 {@code captchaResult} 返回结果为 {@code false}，不需要展示错误提示文案。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/15
 * @since 3.0.0
 **/
public class AliyunCaptchaVerifiedFailureException extends RuntimeException {}
