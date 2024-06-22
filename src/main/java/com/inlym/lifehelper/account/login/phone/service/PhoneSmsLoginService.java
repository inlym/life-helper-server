package com.inlym.lifehelper.account.login.phone.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.inlym.lifehelper.account.login.common.event.LoginByPhoneSmsEvent;
import com.inlym.lifehelper.account.login.phone.entity.LoginSmsTrack;
import com.inlym.lifehelper.account.login.phone.entity.PhoneSmsLoginLog;
import com.inlym.lifehelper.account.login.phone.exception.PhoneCodeExpiredException;
import com.inlym.lifehelper.account.login.phone.exception.PhoneCodeNotMatchException;
import com.inlym.lifehelper.account.login.phone.exception.SmsCheckCodeNotExistsException;
import com.inlym.lifehelper.account.login.phone.mapper.LoginSmsTrackMapper;
import com.inlym.lifehelper.account.login.phone.mapper.PhoneSmsLoginLogMapper;
import com.inlym.lifehelper.account.user.entity.UserAccountPhone;
import com.inlym.lifehelper.account.user.service.UserAccountPhoneService;
import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.common.base.aliyun.sms.constant.SendingStatus;
import com.inlym.lifehelper.common.base.aliyun.sms.service.SmsService;
import com.inlym.lifehelper.common.util.RandomStringUtil;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.inlym.lifehelper.account.login.phone.entity.table.LoginSmsTrackTableDef.LOGIN_SMS_TRACK;

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
public class PhoneSmsLoginService {
    private final SmsService smsService;

    private final LoginSmsTrackMapper loginSmsTrackMapper;

    private final SimpleTokenService simpleTokenService;

    private final PhoneSmsLoginLogMapper phoneSmsLoginLogMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

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
        // TODO
        // 发送前校验：判断是否发送

        // 检验“验证码”是否正确时，使用 [checkCode(20位随机字符串) + code] 的方式替代 [phone + code]，能够大大降低被碰撞的概率。
        String checkCode = RandomStringUtil.generate(32);
        // 短信验证码
        String code = RandomStringUtil.generateNumericString(6);

        // 发送前记录
        LoginSmsTrack inserted = LoginSmsTrack
                .builder()
                .phone(phone)
                .code(code)
                .checkCode(checkCode)
                .ip(ip)
                .preSendTime(LocalDateTime.now())
                .sendingStatus(SendingStatus.UNSENT)
                .build();
        loginSmsTrackMapper.insertSelective(inserted);

        // 正式发送短信
        SendSmsResponseBody result = smsService.sendLoginCode(phone, code);

        // 发送后记录
        LoginSmsTrack updated = LoginSmsTrack
                .builder()
                .id(inserted.getId())
                .resCode(result.getCode())
                .resMessage(result.getMessage())
                .resBizId(result.getBizId())
                .requestId(result.getRequestId())
                .postSendTime(LocalDateTime.now())
                .build();
        loginSmsTrackMapper.update(updated);

        if (result.getCode().equals("OK")) {
            // 短信成功发送情况
            log.info("[SMS] 短信验证码发送成功, phone={}, code={}", phone, code);
        } else {
            log.error("[SMS] 短信验证码发送失败, phone={}, code={}, response={}", phone, code, result);
            // 短信未发送，抛出异常
            // TODO
            // 替换为自定义异常
            throw new RuntimeException("短信发送失败");
        }
    }

    public IdentityCertificate loginBySmsCode(String checkCode, String code) {
        LoginSmsTrack loginSmsTrack = loginSmsTrackMapper.selectOneByCondition(LOGIN_SMS_TRACK.CHECK_CODE.eq(checkCode));

        // 短信验证码不存在
        if (loginSmsTrack == null) {
            throw new SmsCheckCodeNotExistsException();
        }

        // 短信验证码已过期
        if (loginSmsTrack.getPostSendTime().isAfter(LocalDateTime.now().plusMinutes(5L))) {
            throw new PhoneCodeExpiredException();
        }

        LocalDateTime now = LocalDateTime.now();
        LoginSmsTrack updated = UpdateEntity.of(LoginSmsTrack.class, loginSmsTrack.getId());
        if (loginSmsTrack.getFirstAttemptTime() == null) {
            updated.setFirstAttemptTime(now);
        }
        updated.setLastAttemptTime(now);
        UpdateWrapper<LoginSmsTrack> wrapper = UpdateWrapper.of(updated);
        wrapper.set(LOGIN_SMS_TRACK.ATTEMPT_COUNTER, LOGIN_SMS_TRACK.ATTEMPT_COUNTER.add(1));

        if (!Objects.equals(loginSmsTrack.getCode(), code)) {
            loginSmsTrackMapper.update(updated);
            throw new PhoneCodeNotMatchException();
        }

        // 全部校验通过，登录成功情况
        UserAccountPhone userAccountPhone = userAccountPhoneService.getUserAccountPhone(loginSmsTrack.getPhone());

        // 生成鉴权凭据
        IdentityCertificate identityCertificate = simpleTokenService.generateIdentityCertificate(userAccountPhone.getUserId());

        // 记录到日志
        PhoneSmsLoginLog insertedLog = PhoneSmsLoginLog
                .builder()
                .phone(loginSmsTrack.getPhone())
                .userAccountPhoneId(userAccountPhone.getId())
                .userId(userAccountPhone.getUserId())
                .token(identityCertificate.getToken())
                .ip(loginSmsTrack.getIp())
                .loginTime(LocalDateTime.now())
                .code(loginSmsTrack.getCode())
                .build();
        phoneSmsLoginLogMapper.insertSelective(insertedLog);

        // 剩余信息记录到追踪表
        updated.setSucceedTime(now);
        updated.setUserAccountPhoneId(userAccountPhone.getId());
        loginSmsTrackMapper.update(updated);

        // 发布手机号使用短信验证码登录事件
        applicationEventPublisher.publishEvent(new LoginByPhoneSmsEvent(insertedLog));

        return identityCertificate;
    }

    /**
     * （在短信发送前）检查是否达到发送限制
     *
     * <h3>说明
     * <p>目前限制为：
     * <p>1. 每分钟：1条
     * <p>1. 每小时：5条
     *
     * @param phone 手机号
     * @param ip    客户端 IP 地址
     */
    private void checkSendingLimit(String phone, String ip) {
        // TODO
    }
}
