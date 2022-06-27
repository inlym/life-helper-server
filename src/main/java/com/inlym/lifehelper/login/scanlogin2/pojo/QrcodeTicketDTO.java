package com.inlym.lifehelper.login.scanlogin2.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 扫码登录接口，由客户端提交的凭证编码信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/14
 * @since 1.1.0
 **/
@Data
public class QrcodeTicketDTO {
    /** 凭证编码 */
    @NotEmpty
    private String ticket;
}
