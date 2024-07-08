package com.weutil.common.base.aliyun.sms.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.weutil.common.base.aliyun.sms.exception.SmsCommonException;
import com.weutil.common.base.aliyun.sms.model.SmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里云短信服务
 *
 * <h2>说明
 * <p>封装阿里云短信相关方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/12
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {
    /** 签名名称 */
    private static final String SIGN_NAME = "小鸣AI";

    /** 响应数据表达成功的 code 值 */
    private static final String SUCCESS_CODE = "OK";

    private final SmsClient smsClient;

    /**
     * 发送登录用途的短信验证码
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param code  6位数字格式的验证码，示例值：{@code 123456}
     *
     * @date 2024/6/12
     * @since 2.3.0
     */
    public SendSmsResponseBody sendLoginCode(String phone, String code) {
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(SIGN_NAME)
                .setTemplateCode("SMS_468360281")
                .setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponseBody result = smsClient.sendSms(sendSmsRequest).getBody();
            if (Objects.equals(result.getCode(), SUCCESS_CODE)) {
                log.info("[SendSms] 短信发送成功, phone={}, code={}, BizId={}", phone, code, result.getBizId());
                return result;
            } else {
                log.error("[SendSms] 短信发送失败，phone={}, code={},error_code={}, message={}", phone, code, result.getCode(), result.getMessage());
                throw new SmsCommonException();
            }
        } catch (SmsCommonException e) {
            throw new SmsCommonException();
        } catch (Exception e) {
            log.error("[SendSms] 短信发送失败，错误消息：{}", e.getMessage());
            throw new SmsCommonException();
        }
    }
}
