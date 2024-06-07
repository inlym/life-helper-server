package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.inlym.lifehelper.common.base.aliyun.oss.config.UserUploadBucketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户直传专用存储空间服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class UserUploadBucketService {
    private final UserUploadBucketProperties properties;
}
