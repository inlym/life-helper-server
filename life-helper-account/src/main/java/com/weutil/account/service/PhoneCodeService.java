package com.weutil.account.service;

import com.weutil.account.exception.NotSameIpException;
import com.weutil.account.exception.PhoneCodeAttemptExceededException;
import com.weutil.account.exception.PhoneCodeNotMatchException;
import com.weutil.common.util.RandomStringUtil;
import com.weutil.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

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
     * 发送短信验证码
     *
     * <h3>说明
     * <p>记录客户端 IP 地址的用途是：要求2个环节的 IP 地址一致，防止伪造请求攻击。
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/10/18
     * @since 2.3.0
     */
    public String send(String phone, String ip) {
        // 短信验证码（6位纯数字）
        String code = RandomStringUtil.generateNumericString(6);

        // 正式发送短信
        smsService.sendPhoneCode(phone, code, ip);

        // 记录在 Redis，用于后续校验使用
        stringRedisTemplate.opsForValue().set(getPhoneCodeKey(phone, code), ip, Duration.ofMinutes(5L));

        return code;
    }

    /**
     * 生成 Redis 键名
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param code  6位纯数字格式的验证码，示例值：{@code 123456}
     *
     * @date 2024/11/04
     * @since 3.0.0
     */
    private String getPhoneCodeKey(String phone, String code) {
        return "auth:phone-code:" + phone + ":" + code;
    }

    /**
     * 对手机号和验证码进行校验（若校验不通过则直接报错）
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param code  6位纯数字格式的验证码，示例值：{@code 123456}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/11/04
     * @since 3.0.0
     */
    public void verifyOrThrow(String phone, String code, String ip) {
        checkAttemptCounter(phone, ip);

        String result = stringRedisTemplate.opsForValue().get(getPhoneCodeKey(phone, code));
        if (result == null) {
            throw new PhoneCodeNotMatchException();
        }

        // 此处此处，说明验证码 code 输入正确
        if (!result.equals(ip)) {
            throw new NotSameIpException();
        }
    }

    /**
     * 检查尝试次数是否超出限制
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/11/04
     * @since 3.0.0
     */
    private void checkAttemptCounter(String phone, String ip) {
        // 本期内只限定5分钟内的，后续再补充：1小时、1天

        String key1 = "auth:phone-code:attempt-5m:" + phone;
        String key2 = "auth:phone-code:attempt-5m:" + ip;

        String s1 = stringRedisTemplate.opsForValue().get(key1);
        if (s1 != null) {
            long counter1 = Long.parseLong(s1);
            if (counter1 >= 10) {
                throw new PhoneCodeAttemptExceededException();
            }
        }

        String s2 = stringRedisTemplate.opsForValue().get(key2);
        if (s2 != null) {
            long counter2 = Long.parseLong(s2);
            if (counter2 >= 10) {
                throw new PhoneCodeAttemptExceededException();
            }
        }

        // 增加尝试次数计数
        stringRedisTemplate.opsForValue().increment(key1);
        stringRedisTemplate.expire(key1, Duration.ofMinutes(5L));
        stringRedisTemplate.opsForValue().increment(key2);
        stringRedisTemplate.expire(key2, Duration.ofMinutes(5L));
    }
}
