package com.inlym.lifehelper.account.login.phone.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 发送短信验证码的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/23
 * @since 2.3.0
 **/
@Data
public class SendingSmsDTO {
    /** 手机号 */
    @NotEmpty
    private String phone;
}
