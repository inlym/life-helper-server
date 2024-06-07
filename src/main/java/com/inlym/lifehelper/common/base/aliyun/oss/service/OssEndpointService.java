package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.inlym.lifehelper.common.base.aliyun.oss.constant.OssEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 阿里云 OSS 访问地址服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class OssEndpointService {
    private final Environment environment;

    /**
     * 获取 OSS 访问地址
     *
     * <h3>说明
     * <p>除了在生产环境（{@code prod}）使用内网地址访问外，其余情况均直接使用公网访问。
     */
    public String getEndpoint() {
        String envName = environment.getProperty("spring.profiles.active");
        if ("prod".equalsIgnoreCase(envName)) {
            return OssEndpoint.INTERNAL_NETWORK.getDomain();
        }

        return OssEndpoint.PUBLIC_NETWORK.getDomain();
    }
}
