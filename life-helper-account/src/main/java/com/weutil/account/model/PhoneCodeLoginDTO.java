package com.weutil.account.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 使用短信验证码方式登录的请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@Data
public class PhoneCodeLoginDTO {
    /** 手机号 */
    @NotEmpty
    private String phone;

    /** 短信验证码 */
    @NotEmpty
    private String code;
}