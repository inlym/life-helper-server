package com.inlym.lifehelper.common.base.aliyun.sms.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务
 *
 * <h2>主要用途
 * <p>封装阿里云短信的发送功能。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/7/18
 * @since 2.0.2
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AliyunSmsService {
    /**
     * 短信签名
     */
    private static final String SIGN_NAME = "快乐短文";

    private final AliyunSmsProperties properties;

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param code  验证码，目前为6位数字
     *
     * @date 2023/7/20
     * @see <a href="https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/SendSms">发送短信</a>
     * @since 2.0.2
     */

    public void sendVerificationCode(String phone, String code) {
        SendSmsRequest request = new SendSmsRequest()
            .setPhoneNumbers(phone)
            .setSignName(SIGN_NAME)
            .setTemplateCode("SMS_271410655")
            .setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponse response = getClient().sendSms(request);
            SendSmsResponseBody body = response.getBody();
            log.info("请求参数 → phone={},code={} 响应数据 → Message={},RequestId={},Code={},BizId={},", phone, code, body.getMessage(), body.getRequestId(),
                     body.getCode(), body.getBizId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取短信服务客户端
     *
     * @date 2023/7/20
     * @see <a href="https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/SendSms">发送短信</a>
     * @since 2.0.2
     */
    @SneakyThrows
    private Client getClient() {
        Config config = new Config()
            .setAccessKeyId(properties.getAccessKeyId())
            .setAccessKeySecret(properties.getAccessKeySecret())
            .setEndpoint("dysmsapi.aliyuncs.com");

        return new Client(config);
    }
}
