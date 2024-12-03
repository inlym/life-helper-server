package com.weutil.sms.model;

import com.aliyun.teaopenapi.models.Config;

/**
 * 二次封装的阿里云短信客户端
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/16
 * @since 3.0.0
 **/
public class AliyunSmsClient extends com.aliyun.dysmsapi20170525.Client {
    public AliyunSmsClient(Config config) throws Exception {
        super(config);
    }
}
