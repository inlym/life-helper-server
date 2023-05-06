package com.inlym.lifehelper.user.info.service;

import com.inlym.lifehelper.common.constant.Endpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 默认用户信息数据提供器
 *
 * <h2>主要用途
 * <p>当用户数据为空时，提供可用于客户端展示的默认数据。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/4
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserInfoProvider {

    /**
     * 获取昵称
     *
     * @date 2023/5/4
     * @since 2.0.0
     */
    public String getNickName() {
        return "小鸣助手用户";
    }

    /**
     * 获取头像 URL
     *
     * @date 2023/5/4
     * @since 2.0.0
     */
    public String getAvatarUrl() {
        return Endpoint.LOGO_URL;
    }
}
