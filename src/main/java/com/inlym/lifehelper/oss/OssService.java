package com.inlym.lifehelper.oss;

import com.aliyun.oss.model.BucketInfo;

public interface OssService {
    BucketInfo getBucketInfo(String bucketName);
}
