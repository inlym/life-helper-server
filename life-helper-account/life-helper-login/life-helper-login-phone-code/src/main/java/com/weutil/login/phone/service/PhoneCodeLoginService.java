package com.weutil.login.phone.service;

import com.weutil.account.phone.entity.PhoneAccount;
import com.weutil.account.phone.service.PhoneAccountService;
import com.weutil.common.model.IdentityCertificate;
import com.weutil.common.service.IdentityCertificateService;
import com.weutil.login.common.entity.LoginLog;
import com.weutil.login.common.mapper.LoginLogMapper;
import com.weutil.login.common.model.LoginChannel;
import com.weutil.login.common.model.LoginType;
import com.weutil.sms.entity.PhoneCode;
import com.weutil.sms.service.PhoneCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PhoneCodeService phoneCodeService;

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @param ip    客户端 IP 地址
     *
     * @date 2024/6/13
     * @since 2.3.0
     */
    public PhoneCode sendSms(String phone, String ip) {
        return phoneCodeService.send(phone, ip);
    }

    /**
     * 通过短信验证码登录
     *
     * @param checkTicket 校验码
     * @param code        短信验证码
     *
     * @date 2024/06/23
     * @since 2.3.0
     */
    public IdentityCertificate loginBySmsCode(String checkTicket, String code, String ip) {
        // 以下校验通过才返回手机号，校验失败会直接抛出错误
        String phone = phoneCodeService.verify(checkTicket, code, ip);

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
