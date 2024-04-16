package com.inlym.lifehelper.login.wechat.service;

import com.inlym.lifehelper.common.auth.core.IdentityCertificate;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.extern.wechat.WeChatService;
import com.inlym.lifehelper.extern.wechat.pojo.WeChatSession;
import com.inlym.lifehelper.login.wechat.entity.WeChatLoginLog;
import com.inlym.lifehelper.login.wechat.mapper.WeChatLoginLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信登录服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/15
 * @since 2.1.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class WeChatLoginService {
    private final WeChatService weChatService;

    private final SimpleTokenService simpleTokenService;

    private final WeChatLoginLogMapper weChatLoginLogMapper;

    /**
     * 通过微信小程序中获取的 code 登录
     *
     * @param code 微信小程序中获取的 code
     * @param ip   客户端 IP 地址
     *
     * @since 2.1.0
     */
    public IdentityCertificate loginByCode(String appId, String code, String ip) {
        WeChatSession session = weChatService.getSession(appId, code);
        // TODO
        //        long userId = userAccountService.getUserIdByUnionId(session.getUnionId());
        long userId = 0;
        IdentityCertificate identityCertificate = simpleTokenService.generateIdentityCertificate(userId);

        // 记录登录日志
        WeChatLoginLog entity = WeChatLoginLog
                .builder()
                .code(code)
                .appId(appId)
                .openId(session.getOpenId())
                .unionId(session.getUnionId())
                .sessionKey(session.getSessionKey())
                .userId(userId)
                .token(identityCertificate.getToken())
                .ip(ip)
                .build();
        weChatLoginLogMapper.insertSelective(entity);
        log.info("[微信登录] {}", entity);

        return identityCertificate;
    }
}
