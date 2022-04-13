package com.inlym.lifehelper.login.scanlogin.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 返回给客户端使用的小程序码凭证信息业务对象
 *
 * <h2>说明
 * <p>不同的状态使用的字段不同。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/13
 * @since 1.1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrcodeCredentialBO {
    /** 凭证编号，一般是去掉短横线的 UUID */
    private String ticket;

    /** 生成的小程序码访问地址 */
    private String url;

    /** 状态，这个字段在任何情况下都不为空 */
    private Integer status;

    /** 登录凭证，鉴权成功后返回 */
    private String token;

    /**
     * 创建一个无效的凭证
     *
     * @since 1.1.0
     */
    public static QrcodeCredentialBO invalid() {
        QrcodeCredentialBO credential = new QrcodeCredentialBO();
        credential.setStatus(QrcodeCredential.CredentialStatus.INVALID);
        return credential;
    }

    /**
     * 根据状态创建一个业务对象
     *
     * @param status 凭证状态
     *
     * @since 1.1.0
     */
    public static QrcodeCredentialBO fromStatus(int status) {
        QrcodeCredentialBO bo = new QrcodeCredentialBO();
        bo.setStatus(status);
        return bo;
    }
}
