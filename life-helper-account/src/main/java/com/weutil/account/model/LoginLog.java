package com.weutil.account.model;

import java.time.LocalDateTime;

/**
 * 登录日志接口
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
public interface LoginLog {
    /** 获取用户 ID */
    Long getUserId();

    /** 获取客户端 IP 地址 */
    String getIp();

    /** 获取登录时间 */
    LocalDateTime getLoginTime();
}
