package com.weutil.account.controller;

import com.weutil.account.model.PhoneCodeLoginDTO;
import com.weutil.account.model.SendingSmsDTO;
import com.weutil.account.service.PhoneCodeLoginService;
import com.weutil.aliyun.captcha.service.AliyunCaptchaService;
import com.weutil.common.annotation.ClientIp;
import com.weutil.common.model.IdentityCertificate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 手机短信验证码登录控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
public class PhoneCodeLoginController {
    private final PhoneCodeLoginService phoneCodeLoginService;
    private final AliyunCaptchaService aliyunCaptchaService;

    /**
     * 发送登录使用的短信验证码
     *
     * @param ip  客户端 IP 地址
     * @param dto 请求数据
     *
     * @date 2024/6/23
     * @since 2.3.0
     */
    @PostMapping("/sms/login")
    public Map<String, String> sendSms(@ClientIp String ip, @Valid @RequestBody SendingSmsDTO dto) {
        String phone = dto.getPhone();
        String captchaVerifyParam = dto.getCaptchaVerifyParam();

        aliyunCaptchaService.verifyOrThrow(captchaVerifyParam);
        phoneCodeLoginService.sendSms(phone, ip);

        return Map.of("phone", phone);
    }

    /**
     * 使用短信验证码登录
     *
     * @param ip  客户端 IP 地址
     * @param dto 请求数据
     *
     * @date 2024/6/23
     * @since 2.3.0
     */
    @PostMapping("/login/sms")
    public IdentityCertificate loginBySmsCode(@ClientIp String ip, @Valid @RequestBody PhoneCodeLoginDTO dto) {
        String phone = dto.getPhone();
        String code = dto.getCode();

        return phoneCodeLoginService.loginByPhoneCode(phone, code, ip);
    }
}
