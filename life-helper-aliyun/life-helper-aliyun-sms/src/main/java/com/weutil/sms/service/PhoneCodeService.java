package com.weutil.sms.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.common.util.RandomStringUtil;
import com.weutil.sms.entity.PhoneCode;
import com.weutil.sms.exception.*;
import com.weutil.sms.mapper.PhoneCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.weutil.sms.entity.table.PhoneCodeTableDef.PHONE_CODE;

/**
 * 手机短信验证码服务
 *
 * <h2>说明
 * <p>1. 当前服务类按照架构应该独立一个模块，但由于和短信服务耦合度较高，又无其他独立用途，因此合并放一个模块内了。
 * <p>2. 当前服务类的用途是“验证手机号的归属”，可用于登录/注册，以及修改手机号等操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/8/28
 * @since 3.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneCodeService {
    private final PhoneCodeMapper phoneCodeMapper;
    private final SmsService smsService;

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
    public PhoneCode send(String phone, String ip) {
        // 检查手机号格式是否正确
        checkPhoneFormat(phone);
        // 检查是否达到发送限制
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
        PhoneCode inserted = PhoneCode.builder()
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
        phoneCodeMapper.insertSelective(inserted);

        if (result.getCode().equals("OK")) {
            // 处理短信发送成功情况
            log.info("[SMS] 短信验证码发送成功, phone={}, code={}", phone, code);
            return phoneCodeMapper.selectOneById(inserted.getId());
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
     * @date 2024/6/23
     * @since 2.3.0
     */
    private void checkSendingLimit(String phone, String ip) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PHONE_CODE.ALL_COLUMNS)
                .from(PHONE_CODE)
                .orderBy(PHONE_CODE.POST_SEND_TIME.desc())
                .where(PHONE_CODE.PHONE.eq(phone).or(PHONE_CODE.IP.eq(ip)));

        // 限制每分钟 1 条
        QueryWrapper query1 = queryWrapper.clone().where(PHONE_CODE.POST_SEND_TIME.gt(LocalDateTime.now().minusMinutes(1L)));
        List<PhoneCode> list1 = phoneCodeMapper.selectListByQuery(query1);
        if (!list1.isEmpty()) {
            Duration between = Duration.between(list1.get(0).getPostSendTime(), LocalDateTime.now());
            throw new SmsRateLimitExceededException(Duration.ofMinutes(1L).toSeconds() - between.toSeconds());
        }

        // 限制每小时 5 条
        QueryWrapper query2 = queryWrapper.clone().where(PHONE_CODE.POST_SEND_TIME.gt(LocalDateTime.now().minusHours(1L)));
        List<PhoneCode> list2 = phoneCodeMapper.selectListByQuery(query2);
        if (list1.size() > 5) {
            Duration between = Duration.between(list2.get(0).getPostSendTime(), LocalDateTime.now());
            throw new SmsRateLimitExceededException(Duration.ofHours(1L).toSeconds() - between.toSeconds());
        }
    }

    /**
     * 对手机号和短信验证码进行验证
     *
     * @param checkTicket 校验码
     * @param code        短信验证码
     * @param ip          客户端 IP 地址
     *
     * @return 验证后的手机号
     * @date 2024/8/28
     * @since 3.0.0
     */
    public String verify(String checkTicket, String code, String ip) {
        PhoneCode entity = phoneCodeMapper.selectOneByCondition(PHONE_CODE.CHECK_TICKET.eq(checkTicket));

        // 短信验证码不存在
        if (entity == null) {
            throw new SmsCheckTicketNotExistsException();
        }

        // 已经登录成功成功过了，就不允许再次使用
        if (entity.getSucceedTime() != null) {
            throw new PhoneCodeAlreadyUsedException();
        }

        // 短信验证码已过期
        if (entity.getPostSendTime().plusMinutes(5L).isBefore(LocalDateTime.now())) {
            throw new PhoneCodeExpiredException();
        }

        // 尝试次数超过10次
        if (entity.getAttemptCounter() >= 10) {
            throw new PhoneCodeAttemptExceededException();
        }

        LocalDateTime now = LocalDateTime.now();
        PhoneCode updated = UpdateEntity.of(PhoneCode.class, entity.getId());
        if (entity.getFirstAttemptTime() == null) {
            updated.setFirstAttemptTime(now);
        }
        updated.setLastAttemptTime(now);
        UpdateWrapper<PhoneCode> wrapper = UpdateWrapper.of(updated);
        wrapper.set(PHONE_CODE.ATTEMPT_COUNTER, PHONE_CODE.ATTEMPT_COUNTER.add(1));

        // 短信验证码不匹配
        if (!Objects.equals(entity.getCode(), code)) {
            phoneCodeMapper.update(updated);
            throw new PhoneCodeNotMatchException();
        }

        // 虽然验证码匹配上了，再增加一重保障，要求“获取验证码”操作和“输入验证码”操作的设备是同一个，目前仅验证 IP 地址相同
        if (!Objects.equals(entity.getIp(), ip)) {
            phoneCodeMapper.update(updated);
            throw new NotSameIpException();
        }

        // 全部校验通过，返回手机号
        updated.setSucceedTime(now);
        phoneCodeMapper.update(updated);

        return entity.getPhone();
    }
}
