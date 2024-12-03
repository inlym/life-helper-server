package com.weutil.sms.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.mybatisflex.core.query.QueryWrapper;
import com.weutil.sms.entity.SmsLog;
import com.weutil.sms.exception.InvalidPhoneNumberException;
import com.weutil.sms.exception.SmsRateLimitExceededException;
import com.weutil.sms.mapper.SmsLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.weutil.sms.entity.table.SmsLogTableDef.SMS_LOG;

/**
 * 阿里云短信封装服务
 *
 * <h2>说明
 * <p>对底层方法进一步封装，用于内部调用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/11/4
 * @since 3.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AliyunSmsService {
    private final AliyunSmsApiService aliyunSmsApiService;
    private final SmsLogMapper smsLogMapper;

    /**
     * 发送短信验证码
     *
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param code  6位纯数字格式的验证码，示例值：{@code 123456}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/11/4
     * @since 3.0.0
     */
    public SmsLog sendPhoneCode(String phone, String code, String ip) {
        // 检查手机号格式是否正确
        checkPhoneFormat(phone);
        // 检查是否达到发送限制
        checkSendingLimit(phone, ip);

        // 发送前的时间
        LocalDateTime preSendTime = LocalDateTime.now();

        // 正式发送短信
        SendSmsResponseBody result = aliyunSmsApiService.sendPhoneCode(phone, code);

        // 发送后记录
        SmsLog inserted = SmsLog.builder()
            .phone(phone)
            .code(code)
            .ip(ip)
            .preSendTime(preSendTime)
            .resCode(result.getCode())
            .resMessage(result.getMessage())
            .resBizId(result.getBizId())
            .requestId(result.getRequestId())
            .postSendTime(LocalDateTime.now())
            .build();
        smsLogMapper.insertSelective(inserted);

        return smsLogMapper.selectOneById(inserted.getId());
    }

    /**
     * 检查手机号格式是否正确
     *
     * @param phone 手机号，示例值：{@code 13111111111}
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
     * @param phone 手机号，示例值：{@code 13111111111}
     * @param ip    客户端 IP 地址，示例值：{@code 114.114.114.114}
     *
     * @date 2024/6/23
     * @since 2.3.0
     */
    private void checkSendingLimit(String phone, String ip) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SMS_LOG.ALL_COLUMNS)
            .from(SMS_LOG)
            .orderBy(SMS_LOG.POST_SEND_TIME.desc())
            .where(SMS_LOG.PHONE.eq(phone).or(SMS_LOG.IP.eq(ip)));

        // 限制每分钟 1 条
        QueryWrapper query1 = queryWrapper.clone().where(SMS_LOG.POST_SEND_TIME.gt(LocalDateTime.now().minusMinutes(1L)));
        List<SmsLog> list1 = smsLogMapper.selectListByQuery(query1);
        if (!list1.isEmpty()) {
            Duration between = Duration.between(list1.get(0).getPostSendTime(), LocalDateTime.now());
            throw new SmsRateLimitExceededException(Duration.ofMinutes(1L).toSeconds() - between.toSeconds());
        }

        // 限制每小时 5 条
        QueryWrapper query2 = queryWrapper.clone().where(SMS_LOG.POST_SEND_TIME.gt(LocalDateTime.now().minusHours(1L)));
        List<SmsLog> list2 = smsLogMapper.selectListByQuery(query2);
        if (list1.size() > 5) {
            Duration between = Duration.between(list2.get(0).getPostSendTime(), LocalDateTime.now());
            throw new SmsRateLimitExceededException(Duration.ofHours(1L).toSeconds() - between.toSeconds());
        }
    }
}
