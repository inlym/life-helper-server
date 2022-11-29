package com.inlym.lifehelper.extern.tencentmap.exception;

/**
 * 无效 IP 异常
 *
 * <h2>说明
 * <p>一般情况下，从阿里云 API 网关处获取的客户端 IP 都是真实有效的，但实测获取到的一些 IP 地址，调用腾讯位置服务的 IP 定位，告知 IP 无法定位。
 * 目前暂不清楚原因。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/5
 * @since 1.2.1
 **/
public class InvalidIpException extends RuntimeException {
    public InvalidIpException(String message) {
        super(message);
    }
}
