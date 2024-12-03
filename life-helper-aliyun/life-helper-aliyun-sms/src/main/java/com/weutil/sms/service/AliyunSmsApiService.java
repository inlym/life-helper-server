package com.weutil.sms.service;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.weutil.sms.config.AliyunSmsProperties;
import com.weutil.sms.exception.CreatingSmsClientFailedException;
import com.weutil.sms.exception.SmsSentFailureException;
import com.weutil.sms.model.AliyunSmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里云短信 API 服务
 *
 * <h2>说明
 * <p>封装阿里云短信相关的底层方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/16
 * @since 3.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AliyunSmsApiService {
    /** 响应数据表达成功的 code 值 */
    private static final String SUCCESS_CODE = "OK";

    private final AliyunSmsProperties properties;
    private final AliyunSmsClient aliyunSmsClient;

    /**
     * 发送登录用途的短信验证码
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param code  6位纯数字格式的验证码，示例值：{@code 123456}
     *
     * @date 2024/6/12
     * @since 2.3.0
     */
    public SendSmsResponseBody sendPhoneCode(String phone, String code) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
            .setPhoneNumbers(phone)
            .setSignName(properties.getSignName())
            .setTemplateCode("SMS_468360281")
            .setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponseBody result = aliyunSmsClient.sendSms(sendSmsRequest).getBody();
            if (Objects.equals(result.getCode(), SUCCESS_CODE)) {
                log.info("[SendSms] 短信发送成功, phone={}, code={}, BizId={}", phone, code, result.getBizId());
                return result;
            } else {
                log.error("[SendSms] 短信发送失败，phone={}, code={},error_code={}, message={}", phone, code, result.getCode(), result.getMessage());
                throw new SmsSentFailureException();
            }
        } catch (CreatingSmsClientFailedException e) {
            // 创建客户端异常，原样抛出，由全局异常捕获器处理
            throw new CreatingSmsClientFailedException();
        } catch (Exception e) {
            // 其他异常，统一处理成“发送失败”
            log.error("[SendSms] 短信发送失败，错误消息：{}", e.getMessage());
            throw new SmsSentFailureException();
        }
    }
}
