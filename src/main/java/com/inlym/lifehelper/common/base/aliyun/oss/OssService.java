package com.inlym.lifehelper.common.base.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * OSS 服务类
 *
 * <h2>注意事项
 *
 * <li>当前只用到一个 OSS 储存空间（bucket），使用不同目录存放不同来源的资源。
 *
 * <h2>目录用途
 *
 * <li> 临时调试使用 {@code temp}
 * <li> 微信小程序码图片 {@code wxacode}
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-12
 * @since 1.0.0
 */
@Service
public class OssService {
    private final OSS ossClient;

    /** 存储空间名称 */
    private final String bucketName;

    private final OssProperties ossProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public OssService(OssProperties ossProperties, OSS ossClient) {
        this.ossClient = ossClient;
        this.ossProperties = ossProperties;
        this.bucketName = ossProperties.getBucketName();
    }

    /**
     * 获取随机文件名
     *
     * <h2>说明
     * <p>目前为去掉短横线的 UUID。
     *
     * @since 1.2.3
     */
    public static String getRandomFilename() {
        return UUID
            .randomUUID()
            .toString()
            .toLowerCase()
            .replaceAll("-", "");
    }

    /**
     * 上传文件
     *
     * @param pathname 文件路径，注意不要以 `/` 开头
     * @param content  文件内容
     *
     * @since 1.0.0
     */
    public void upload(String pathname, byte[] content) {
        ossClient.putObject(bucketName, pathname, new ByteArrayInputStream(content));
    }

    /**
     * 转储文件
     *
     * @param dirname 转储的目录（注意不要以 `/` 开头）
     * @param url     资源文件的 URL 地址
     *
     * @return 资源在 OSS 中的路径
     *
     * @since 1.0.0
     */
    public String dump(String dirname, String url) {
        // 文件路径
        String pathname = dirname + "/" + UUID
            .randomUUID()
            .toString()
            .replaceAll("-", "");

        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, pathname, new ByteArrayInputStream(Objects.requireNonNull(response.getBody())));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader("Content-Type", Objects
            .requireNonNull(response
                .getHeaders()
                .getContentType())
            .toString());
        putObjectRequest.setMetadata(metadata);
        ossClient.putObject(putObjectRequest);

        return pathname;
    }

    /**
     * 联结生成资源的完整 URL 地址
     *
     * @param path 资源在 OSS 中的路径
     *
     * @return 完整的 URL 地址
     *
     * @since 1.0.0
     */
    public String concatUrl(String path) {
        if ("".equals(path)) {
            return "";
        }
        return ossProperties.getAliasUrl() + "/" + path;
    }

    /**
     * 生成客户端直传 OSS 凭证
     *
     * <h2>主要用途
     *
     * <p>客户端可使用该凭证直接将文件上传到 OSS，文件无需经过我方服务器
     *
     * @param dirname 目录名称
     *
     * @since 1.0.0
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
