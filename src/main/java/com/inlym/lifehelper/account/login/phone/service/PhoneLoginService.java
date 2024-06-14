package com.inlym.lifehelper.account.login.phone.service;

import com.inlym.lifehelper.account.user.service.UserAccountPhoneService;
import com.inlym.lifehelper.common.base.aliyun.sms.service.SmsService;
import com.inlym.lifehelper.common.util.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 手机号（短信验证码）登录服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/13
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneLoginService {
    private final SmsService smsService;

    private UserAccountPhoneService userAccountPhoneService;

    /**
     * 发送短信验证码
     *
     * <h3>说明
     * <p>记录客户端 IP 地址的用途是：要求2个环节的 IP 地址一致，防止伪造请求攻击。
     *
     * @param phone 手机号
     * @param ip    客户端 IP 地址
     *
     * @date 2024/6/13
     * @since 2.3.0
     */
    public void sendSms(String phone, String ip) {
        // 检验“验证码”是否正确时，使用 [ticketId(20位随机字符串) + code] 的方式替代 [phone + code]，能够大大降低被碰撞的概率。
        String ticketId = RandomStringUtil.generate(20);
        // 短信验证码
        String code = RandomStringUtil.generateNumericString(6);

        // TODO
        // 短信发送也建一张表，追踪过程
    }
}
