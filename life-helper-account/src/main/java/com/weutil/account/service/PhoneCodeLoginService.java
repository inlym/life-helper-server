package com.weutil.account.service;

import com.weutil.account.entity.LoginLog;
import com.weutil.account.entity.PhoneAccount;
import com.weutil.account.mapper.LoginLogMapper;
import com.weutil.account.model.LoginChannel;
import com.weutil.account.model.LoginType;
import com.weutil.common.model.IdentityCertificate;
import com.weutil.common.service.IdentityCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 手机验证码登录服务
 *
 * <h2>主要用途
 * <p>处理使用“短信验证码”进行登录的各个环节。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/23
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneCodeLoginService {
    private final PhoneAccountService phoneAccountService;
    private final IdentityCertificateService identityCertificateService;
    private final LoginLogMapper loginHistoryMapper;
    private final PhoneCodeService phoneCodeService;

    /**
     * 发送短信验证码
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/6/13
     * @since 2.3.0
     */
    public String sendSms(String phone, String ip) {
        return phoneCodeService.send(phone, ip);
    }

    /**
     * 通过短信验证码登录
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param code  6位纯数字格式的验证码，示例值：{@code 123456}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/06/23
     * @since 2.3.0
     */
    public IdentityCertificate loginByPhoneCode(String phone, String code, String ip) {
        phoneCodeService.verifyOnceOrThrow(phone, code, ip);

        // 全部校验通过，登录成功
        PhoneAccount phoneAccount = phoneAccountService.getOrCreatePhoneAccount(phone);

        // 生成鉴权凭据
        IdentityCertificate identityCertificate = identityCertificateService.create(phoneAccount.getUserId());

        // 记录到日志
        LoginLog history = LoginLog.builder()
            .type(LoginType.PHONE_SMS)
            .channel(LoginChannel.WEB)
            .userId(phoneAccount.getUserId())
            .token(identityCertificate.getToken())
            .ip(ip)
            .loginTime(LocalDateTime.now())
            .phoneAccountId(phoneAccount.getId())
            .build();

        loginHistoryMapper.insertSelective(history);

        return identityCertificate;
    }
}
