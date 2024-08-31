package com.weutil.login.phone.controller;

import com.weutil.common.annotation.ClientIp;
import com.weutil.common.model.IdentityCertificate;
import com.weutil.login.phone.model.CheckTicketVO;
import com.weutil.login.phone.model.PhoneCodeLoginDTO;
import com.weutil.login.phone.model.SendingSmsDTO;
import com.weutil.login.phone.service.PhoneCodeLoginService;
import com.weutil.sms.entity.PhoneCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public CheckTicketVO sendSms(@ClientIp String ip, @Valid @RequestBody SendingSmsDTO dto) {
        String phone = dto.getPhone();
        PhoneCode result = phoneCodeLoginService.sendSms(phone, ip);

        return CheckTicketVO.builder().checkTicket(result.getCheckTicket()).build();
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
        String checkTicket = dto.getCheckTicket();
        String code = dto.getCode();

        return phoneCodeLoginService.loginBySmsCode(checkTicket, code, ip);
    }
}
