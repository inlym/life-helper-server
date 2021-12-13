package com.inlym.lifehelper.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketInfo;
import org.springframework.stereotype.Service;

@Service
public class OssServiceImpl implements OssService {
    private final OSS ossClient;

    public OssServiceImpl(OssProperties ossProperties) {
        this.ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

    @Override
    public BucketInfo getBucketInfo(String bucketName) {
        return ossClient.getBucketInfo(bucketName);
    }
}
