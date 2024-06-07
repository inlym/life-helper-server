package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.inlym.lifehelper.common.base.aliyun.oss.config.StaticResourceBucketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 静态资源专用存储空间服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class StaticResourceBucketService {
    private final StaticResourceBucketProperties properties;
}
