package com.inlym.lifehelper.external.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OSSService {
    private final OSS ossClient;

    private final String bucketName;

    private final OSSProperties ossProperties;

    public OSSService(OSSProperties ossProperties, OSS ossClient) {
        this.ossClient = ossClient;
        this.ossProperties = ossProperties;
        this.bucketName = ossProperties.getBucketName();
    }

    /**
     * 上传文件
     *
     * @param pathname 文件路径，注意不要以 `/` 开头
     * @param content  文件内容
     */
    public void upload(String pathname, byte[] content) {
        ossClient.putObject(bucketName, pathname, new ByteArrayInputStream(content));
    }

    /**
     * 转储文件
     *
     * @param pathname 文件路径，注意不要以 `/` 开头
     * @param url      资源文件的 URL 地址
     */
    public void dump(String pathname, String url) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        ossClient.putObject(bucketName, pathname, inputStream);
    }

    /**
     * 生成客户端直传 OSS 凭证（客户端可使用该凭证直接将文件上传到 OSS，文件无需经过我方服务器）
     *
     * @param dirname 目录名称
     */
    public Map<String, String> createClientToken(String dirname) {
        // 凭证有效时长（分钟）：120 分钟
        long freshMinutes = 120;

        // 上传文件的最大体积：50MB
        long maxSize = 50 * 1024 * 1024;

        // 文件名：去掉短横线的 UUID
        String filename = UUID
            .randomUUID()
            .toString()
            .replaceAll("-", "");

        // 文件完整路径
        String pathname = dirname + "/" + filename;

        // 凭证有效期结束时间（时间戳）
        long expireEndTime = System.currentTimeMillis() + freshMinutes * 60 * 1000;
        Date expiration = new Date(expireEndTime);

        PolicyConditions policyConditions = new PolicyConditions();
        // 指定文件体积（范围）
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
        // 指定文件路径（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, pathname);
        // 指定存储空间（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, "bucket", bucketName);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);

        String policy = BinaryUtil.toBase64String(binaryData);
        String signature = ossClient.calculatePostSignature(postPolicy);

        Map<String, String> map = new LinkedHashMap<>();
        map.put("OSSAccessKeyId", ossProperties.getAccessKeyId());
        map.put("url", ossProperties.getAliasUrl());
        map.put("key", pathname);
        map.put("policy", policy);
        map.put("signature", signature);

        return map;
    }
}
