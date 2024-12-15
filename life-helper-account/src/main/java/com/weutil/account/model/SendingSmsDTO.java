package com.weutil.account.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 发送短信验证码的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Data
public class SendingSmsDTO {
    /** 手机号 */
    @NotEmpty
    private String phone;

    /** 验证码校验参数 */
    @NotEmpty
    private String captchaVerifyParam;
}
