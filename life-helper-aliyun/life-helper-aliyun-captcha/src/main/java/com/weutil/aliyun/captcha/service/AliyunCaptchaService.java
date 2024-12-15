package com.weutil.aliyun.captcha.service;

import com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaResponse;
import com.weutil.aliyun.captcha.exception.AliyunCaptchaVerifiedFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里云验证码服务
 *
 * <h2>说明
 * <p>对 API 做进一步封装，用于内部调用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/16
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AliyunCaptchaService {
    private final AliyunCaptchaApiService aliyunCaptchaApiService;

    /**
     * 检验验证码参数（校验通过或直接抛出异常）
     *
     * <h3>说明
     * <p>处理成抛出错误而不是直接返回布尔值的原因是：能够中断流程，否则一系列判断很麻烦。
     *
     * @param captchaVerifyParam 由验证码脚本回调的验证参数
     *
     * @date 2024/12/15
     * @since 3.0.0
     */
    public void verifyOrThrow(String captchaVerifyParam) {
        boolean result = verifyCaptcha(captchaVerifyParam);
        if (!result) {
            throw new AliyunCaptchaVerifiedFailureException();
        }
    }

    /**
     * 校验验证码参数
     *
     * @param captchaVerifyParam 由验证码脚本回调的验证参数
     *
     * @return 是否验证通过
     * @date 2024/10/16
     * @since 3.0.0
     */
    public boolean verifyCaptcha(String captchaVerifyParam) {
        VerifyIntelligentCaptchaResponse response = aliyunCaptchaApiService.verifyCaptcha(captchaVerifyParam);
        if (!response.getBody().getSuccess() || !"Success".equalsIgnoreCase(response.getBody().getCode())) {
            log.debug("验证码校验失败，响应结果：{}", response.getBody());
            return false;
        }

        if (!response.getBody().getResult().verifyResult) {
            log.debug("验证码校验失败，原因码：{}", response.getBody().getResult().verifyCode);
            return false;
        }

        return true;
    }
}
