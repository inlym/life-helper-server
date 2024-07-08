package com.weutil.account.login.wechat.service;

import com.weutil.account.login.common.event.LoginByWeChatAccountEvent;
import com.weutil.account.login.wechat.entity.WeChatLoginLog;
import com.weutil.account.login.wechat.mapper.WeChatLoginLogMapper;
import com.weutil.account.user.entity.UserAccountWeChat;
import com.weutil.account.user.model.WeChatAccountInfo;
import com.weutil.account.user.service.UserAccountWeChatService;
import com.weutil.common.auth.core.IdentityCertificate;
import com.weutil.common.auth.simpletoken.SimpleTokenService;
import com.weutil.external.wechat.WeChatService;
import com.weutil.external.wechat.pojo.WeChatSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 微信登录服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/10
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class WeChatLoginService {
    private final WeChatService weChatService;

    private final SimpleTokenService simpleTokenService;

    private final WeChatLoginLogMapper weChatLoginLogMapper;

    private final UserAccountWeChatService userAccountWeChatService;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 通过微信 code 的方式登录
     *
     * @param appId 小程序开发者 ID
     * @param code  微信小程序端通过 `wx.login` 获取的 code
     * @param ip    客户端 IP 地址
     *
     * @return 登录凭据
     * @date 2024/6/10
     * @since 2.3.0
     */
    public IdentityCertificate loginByWeChatCode(String appId, String code, String ip) {
        WeChatSession session = weChatService.getSession(appId, code);
        WeChatAccountInfo weChatAccountInfo = WeChatAccountInfo.builder().appId(appId).openId(session.getOpenId()).unionId(session.getUnionId()).build();
        UserAccountWeChat weChatUserAccount = userAccountWeChatService.getOrCreateWeChatUserAccount(weChatAccountInfo);

        // 生成鉴权凭据
        IdentityCertificate identityCertificate = simpleTokenService.generateIdentityCertificate(weChatUserAccount.getUserId());

        // 记录登录日志
        WeChatLoginLog inserted = WeChatLoginLog
                .builder()
                .code(code)
                .appId(appId)
                .openId(session.getOpenId())
                .unionId(session.getUnionId())
                .sessionKey(session.getSessionKey())
                .userAccountWeChatId(weChatUserAccount.getId())
                .userId(weChatUserAccount.getUserId())
                .token(identityCertificate.getToken())
                .ip(ip)
                .loginTime(LocalDateTime.now())
                .build();
        weChatLoginLogMapper.insertSelective(inserted);
        log.info("[微信登录] 登录日志= {}", inserted);

        // 发布微信登录事件
        applicationEventPublisher.publishEvent(new LoginByWeChatAccountEvent(inserted));

        return identityCertificate;
    }
}
