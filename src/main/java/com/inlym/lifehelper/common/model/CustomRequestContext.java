package com.inlym.lifehelper.common.model;

import com.inlym.lifehelper.common.constant.LogName;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * 自定义请求上下文
 *
 * <h2>主要用途
 * <p>将所有用到的请求域字段数据存储在当前对象中，以便后续使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/23
 * @since 1.7.0
 **/
@Data
public class CustomRequestContext {
    /** 在请求域属性使用的名称 */
    public static String attributeName = "CUSTOM_REQUEST_CONTEXT";

    /**
     * 请求 ID
     *
     * <h2>说明
     * <p>生产环境中会由 API 网关在请求头中传入，在开发环境需模拟生成该值。
     */
    private String requestId;

    /** 请求时间 */
    private LocalDateTime requestTime;

    /** 请求方法 */
    private String method;

    /** 请求路径 */
    private String path;

    /** 请求地址（路径 + 请求参数） */
    private String url;

    /**
     * 客户端 IP 地址
     *
     * <h2>说明
     * <p>生产环境中会由 API 网关在请求头中传入，而不是通过连接直接获取。
     */
    private String clientIp;

    /**
     * 用户 ID
     *
     * <h2>说明
     * <p>务必在鉴权通过后再存入。
     */
    private Integer userId;

    // 备注（2023.02.02）
    // 自定义 setter 的原因是：代理赋值过程，保证每个变量最多被赋值1次。

    public void setRequestId(String requestId) {
        Assert.isNull(this.requestId, "自定义请求上下文字段被重复赋值！");
        this.requestId = requestId;

        MDC.put(LogName.REQUEST_ID, requestId);
    }

    public void setRequestTime(LocalDateTime requestTime) {
        Assert.isNull(this.requestTime, "自定义请求上下文字段被重复赋值！");
        this.requestTime = requestTime;
    }

    public void setMethod(String method) {
        Assert.isNull(this.method, "自定义请求上下文字段被重复赋值！");
        this.method = method;

        MDC.put(LogName.METHOD, method);
    }

    public void setPath(String path) {
        Assert.isNull(this.path, "自定义请求上下文字段被重复赋值！");
        this.path = path;
    }

    public void setUrl(String url) {
        Assert.isNull(this.url, "自定义请求上下文字段被重复赋值！");
        this.url = url;

        MDC.put(LogName.URL, url);
    }

    public void setClientIp(String clientIp) {
        Assert.isNull(this.clientIp, "自定义请求上下文字段被重复赋值！");
        this.clientIp = clientIp;

        MDC.put(LogName.CLIENT_IP, clientIp);
    }

    public void setUserId(Integer userId) {
        Assert.isNull(this.userId, "自定义请求上下文字段被重复赋值！");
        this.userId = userId;

        MDC.put(LogName.USER_ID, String.valueOf(userId));
    }
}
