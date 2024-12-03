package com.weutil.aliyun.captcha.service;

import com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaRequest;
import com.aliyun.captcha20230305.models.VerifyIntelligentCaptchaResponse;
import com.weutil.aliyun.captcha.config.AliyunCaptchaProperties;
import com.weutil.aliyun.captcha.model.AliyunCaptchaClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里云验证码 API 服务
 *
 * <h2>说明
 * <p>仅用于封装 API 请求，不做任何业务处理。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/16
 * @see <a href="https://help.aliyun.com/zh/captcha/captcha2-0/user-guide/server-integration">服务端接入</a>
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AliyunCaptchaApiService {
    private final AliyunCaptchaClient aliyunCaptchaClient;
    private final AliyunCaptchaProperties properties;

    /**
     * 校验验证码参数
     *
     * @param captchaVerifyParam 由验证码脚本回调的验证参数
     *
     * @date 2024/10/16
     * @see <a href="https://help.aliyun.com/zh/captcha/captcha2-0/user-guide/server-integration">调用VerifyIntelligentCaptcha接口</a>
     * @since 3.0.0
     */
    @SneakyThrows
    public VerifyIntelligentCaptchaResponse verifyCaptcha(String captchaVerifyParam) {
        VerifyIntelligentCaptchaRequest request = new VerifyIntelligentCaptchaRequest();
        request.setCaptchaVerifyParam(captchaVerifyParam);
        request.setSceneId(properties.getSceneId());

        return aliyunCaptchaClient.verifyIntelligentCaptcha(request);
    }
}
