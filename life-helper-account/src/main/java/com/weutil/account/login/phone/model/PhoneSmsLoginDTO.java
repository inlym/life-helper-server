package com.weutil.account.login.phone.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 使用短信验证码方式登录的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/23
 * @since 2.3.0
 **/
@Data
public class PhoneSmsLoginDTO {
    /** 用于关联短信验证码的“校验码” */
    @NotEmpty
    private String checkTicket;

    /** 短信验证码 */
    @NotEmpty
    private String code;
}
