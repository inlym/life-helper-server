package com.inlym.lifehelper.account.login.phone.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.inlym.lifehelper.account.login.common.event.LoginByPhoneSmsEvent;
import com.inlym.lifehelper.account.login.phone.entity.LoginSmsTrack;
import com.inlym.lifehelper.account.login.phone.entity.PhoneSmsLoginLog;
import com.inlym.lifehelper.account.login.phone.exception.*;
import com.inlym.lifehelper.account.login.phone.mapper.LoginSmsTrackMapper;
import com.inlym.lifehelper.account.login.phone.mapper.PhoneSmsLoginLogMapper;
import com.inlym.lifehelper.account.user.entity.UserAccountPhone;
import com.inlym.lifehelper.account.user.service.UserAccountPhoneService;
import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.common.base.aliyun.sms.constant.SendingStatus;
import com.inlym.lifehelper.common.base.aliyun.sms.service.SmsService;
import com.inlym.lifehelper.common.util.RandomStringUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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

    private final UserAccountPhoneService userAccountPhoneService;

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
    public LoginSmsTrack sendSms(String phone, String ip) {
        // 发送前校验：判断是否发送
        checkSendingLimit(phone, ip);

        // 检验“验证码”是否正确时，使用 [checkTicket(32位随机字符串) + code] 的方式替代 [phone + code]，能够大大降低被碰撞的概率。
        String checkTicket = RandomStringUtil.generate(32);
        // 短信验证码（6位纯数字）
        String code = RandomStringUtil.generateNumericString(6);
        // 发送前的时间
        LocalDateTime preSendTime = LocalDateTime.now();

        // 正式发送短信
        SendSmsResponseBody result = smsService.sendLoginCode(phone, code);

        // 发送后记录
        LoginSmsTrack inserted = LoginSmsTrack
                .builder()
                .phone(phone)
                .code(code)
                .checkTicket(checkTicket)
                .ip(ip)
                .preSendTime(preSendTime)
                .resCode(result.getCode())
                .resMessage(result.getMessage())
                .resBizId(result.getBizId())
                .requestId(result.getRequestId())
                .postSendTime(LocalDateTime.now())
                .build();

        if (result.getCode().equals("OK")) {
            // 短信发送成功情况
            inserted.setSendingStatus(SendingStatus.SENT);
            loginSmsTrackMapper.insertSelective(inserted);
            log.info("[SMS] 短信验证码发送成功, phone={}, code={}", phone, code);
            return loginSmsTrackMapper.selectOneById(inserted.getId());
        } else {
            // 短信发送失败情况
            inserted.setSendingStatus(SendingStatus.UNSENT);
            loginSmsTrackMapper.insertSelective(inserted);
            log.error("[SMS] 短信验证码发送失败, phone={}, code={}, response={}", phone, code, result);
            // 短信未发送，抛出异常
            throw new SmsSentFailureException();
        }
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
     *
     * @date 2024/06/23
     * @since 2.3.0
     */
    private void checkSendingLimit(String phone, String ip) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(LOGIN_SMS_TRACK.ALL_COLUMNS)
                .from(LOGIN_SMS_TRACK)
                .orderBy(LOGIN_SMS_TRACK.POST_SEND_TIME.desc())
                .where(LOGIN_SMS_TRACK.PHONE.eq(phone))
                .or(LOGIN_SMS_TRACK.IP.eq(ip));

        // 限制每分钟 1 条
        QueryWrapper query1 = queryWrapper.where(LOGIN_SMS_TRACK.POST_SEND_TIME.gt(LocalDateTime.now().minusMinutes(1L)));
        List<LoginSmsTrack> list1 = loginSmsTrackMapper.selectListByQuery(query1);
        if (!list1.isEmpty()) {
            Duration between = Duration.between(list1.get(0).getPostSendTime(), LocalDateTime.now());
            throw new SmsRateLimitExceededException(Duration.ofMinutes(1L).toSeconds() - between.toSeconds());
        }

        // 限制每小时 5 条
        QueryWrapper query2 = queryWrapper.where(LOGIN_SMS_TRACK.POST_SEND_TIME.gt(LocalDateTime.now().minusHours(1L)));
        List<LoginSmsTrack> list2 = loginSmsTrackMapper.selectListByQuery(query2);
        if (list1.size() > 5) {
            Duration between = Duration.between(list2.get(0).getPostSendTime(), LocalDateTime.now());
            throw new SmsRateLimitExceededException(Duration.ofHours(1L).toSeconds() - between.toSeconds());
        }
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
        LoginSmsTrack loginSmsTrack = loginSmsTrackMapper.selectOneByCondition(LOGIN_SMS_TRACK.CHECK_TICKET.eq(checkTicket));

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

        // 虽然验证码匹配上了，再增加一重保障，要求“获取验证码”操作和“输入验证码”操作的设备是同一个，目前仅验证 IP 地址相同
        if (!Objects.equals(loginSmsTrack.getIp(), ip)) {
            loginSmsTrackMapper.update(updated);
            throw new NotSameIpException();
        }

        // 全部校验通过，登录成功情况
        UserAccountPhone userAccountPhone = userAccountPhoneService.getOrCreateUserAccountPhone(loginSmsTrack.getPhone());

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
}
