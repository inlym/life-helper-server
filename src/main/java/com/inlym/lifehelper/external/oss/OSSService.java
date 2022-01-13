package com.inlym.lifehelper.external.oss;

import com.aliyun.oss.OSS;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class OSSService {
    private final OSS ossClient;

    private final String bucketName;

    public OSSService(OSSProperties ossProperties, OSS ossClient) {
        this.ossClient = ossClient;
        this.bucketName = ossProperties.getBucketName();
    }

    /**
     * 上传文件
     *
     * @param filename 文件名称（包含路径部分），注意不要以 `/` 开头
     * @param content  文件内容
     */
    public void upload(String filename, byte[] content) {
        ossClient.putObject(bucketName, filename, new ByteArrayInputStream(content));
    }
}
