package com.weutil.common.base.aliyun.sms.model;

import com.aliyun.teaopenapi.models.Config;

/**
 * 二次封装的阿里云短信客户端
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/12
 * @since 2.3.0
 **/
public class SmsClient extends com.aliyun.dysmsapi20170525.Client {
    public SmsClient(Config config) throws Exception {
        super(config);
    }
}
