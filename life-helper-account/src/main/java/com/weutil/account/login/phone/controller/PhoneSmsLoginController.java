package com.weutil.account.login.phone.controller;

import com.weutil.account.login.phone.entity.LoginSmsTrack;
import com.weutil.account.login.phone.model.CheckTicketVO;
import com.weutil.account.login.phone.model.PhoneSmsLoginDTO;
import com.weutil.account.login.phone.model.SendingSmsDTO;
import com.weutil.account.login.phone.service.PhoneSmsLoginService;
import com.weutil.common.annotation.ClientIp;
import com.weutil.common.auth.core.IdentityCertificate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信验证码登录控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/23
 * @since 2.3.0
 **/
@RestController
@RequiredArgsConstructor
public class PhoneSmsLoginController {
    private final PhoneSmsLoginService phoneSmsLoginService;

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
        LoginSmsTrack result = phoneSmsLoginService.sendSms(phone, ip);

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
    public IdentityCertificate loginBySmsCode(@ClientIp String ip, @Valid @RequestBody PhoneSmsLoginDTO dto) {
        String checkTicket = dto.getCheckTicket();
        String code = dto.getCode();

        return phoneSmsLoginService.loginBySmsCode(checkTicket, code, ip);
    }
}
