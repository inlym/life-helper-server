package com.weutil.aliyun.captcha.service;

import com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里云验证码服务
 *
 * <h2>说明
 * <p>对 API 做进一步封装，用于内部调用
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

        return response.getBody().getResult().getVerifyResult();
    }
}
