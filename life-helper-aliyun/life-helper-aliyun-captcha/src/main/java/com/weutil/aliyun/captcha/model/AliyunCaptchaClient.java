package com.weutil.aliyun.captcha.model;

import com.aliyun.captcha20230305.Client;
import com.aliyun.teaopenapi.models.Config;

/**
 * 阿里云验证码服务客户端
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/16
 * @since 3.0.0
 **/
public class AliyunCaptchaClient extends Client {
    public AliyunCaptchaClient(Config config) throws Exception {
        super(config);
    }
}
