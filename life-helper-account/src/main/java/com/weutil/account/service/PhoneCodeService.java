package com.weutil.account.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.weutil.common.util.RandomStringUtil;
import com.weutil.sms.exception.InvalidPhoneNumberException;
import com.weutil.sms.exception.SmsSentFailureException;
import com.weutil.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 手机号验证码服务
 *
 * <h2>说明
 * <p>封装短信验证码的发送、验证环节。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/18
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneCodeService {
    private final StringRedisTemplate stringRedisTemplate;
    private final SmsService smsService;

    /**
     * 生成 Redis 键名
     *
     * @param phone 手机号
     * @param code  短信验证码
     *
     * @date 2024/10/18
     * @since 3.0.0
     */
    private String generateRedisKey(String phone, String code) {
        return "auth:phone-code:" + phone + ":" + code;
    }

    /**
     * 发送短信验证码
     *
     * <h3>说明
     * <p>记录客户端 IP 地址的用途是：要求2个环节的 IP 地址一致，防止伪造请求攻击。
     *
     * @param phone 手机号
     * @param ip    客户端 IP 地址
     *
     * @date 2024/10/18
     * @since 2.3.0
     */
    public String send(String phone, String ip) {
        // ==================== 发送前的检查 ====================

        // 检查手机号格式是否正确
        checkPhoneFormat(phone);

        // ==================== 发送环节 ====================

        // 短信验证码（6位纯数字）
        String code = RandomStringUtil.generateNumericString(6);

        // 正式发送短信
        SendSmsResponseBody result = smsService.sendLoginCode(phone, code);

        if (result.getCode().equals("OK")) {
            // 处理短信发送成功情况
            log.info("[SMS] 短信验证码发送成功, phone={}, code={}", phone, code);

            // TODO: redis 存储

            return code;
        } else {
            // 处理短信发送失败情况
            log.error("[SMS] 短信验证码发送失败, phone={}, code={}, response={}", phone, code, result);
            // 短信未发送，抛出异常
            throw new SmsSentFailureException();
        }
    }

    /**
     * 检查手机号格式是否正确
     *
     * @param phone 手机号
     *
     * @date 2024/6/24
     * @since 2.3.0
     */
    private void checkPhoneFormat(String phone) {
        String regex = "^1\\d{10}$";
        if (!phone.matches(regex)) {
            throw new InvalidPhoneNumberException();
        }
    }

    /**
     * 对手机号和验证码进行校验
     *
     * @param phone 手机号
     * @param code  短信验证码
     * @param ip    客户端 IP 地址
     *
     * @return 是否验证通过
     * @date 2024/10/18
     * @since 3.3.0
     */
    public boolean verify(String phone, String code, String ip) {
        // 检查手机号（phone）是否被列入了黑名单
        // TODO

        // 检查 IP 地址是否被列入了黑名单
        // TODO
        
        return false;
    }
}
