package com.inlym.lifehelper.common.base.aliyun.oss.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 阿里云 OSS 综合封装服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class OssService {
    private final CentralBucketService centralBucketService;

    private final UserUploadBucketService userUploadBucketService;
}
