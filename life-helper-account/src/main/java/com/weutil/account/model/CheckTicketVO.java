package com.weutil.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信校验码视图对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckTicketVO {
    /**
     * 校验码
     *
     * <h3>用途
     * <p>替代手机号，用于和“短信验证码”匹配进行校验，极大降低被“碰撞”的概率。
     */
    private String checkTicket;
}
