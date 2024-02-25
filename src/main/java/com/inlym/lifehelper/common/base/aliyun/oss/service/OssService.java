package com.inlym.lifehelper.common.base.aliyun.oss.service;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.inlym.lifehelper.common.base.aliyun.oss.config.AliyunOssProperties;
import com.inlym.lifehelper.common.base.aliyun.oss.constant.AliyunOssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.model.OssPostCredential;
import com.inlym.lifehelper.common.util.ExtnameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

/**
 * 阿里云 OSS 服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/24
 * @since 2.2.0
 **/
@Service
@RequiredArgsConstructor
public class OssService {
    private final OSS ossClient;

    private final AliyunOssProperties properties;

    /**
     * 上传文件
     *
     * <h2>主要用途
     * <p>用户在服务器获取外部资源，再上传至 OSS。
     *
     * @param pathname 文件路径，注意不要以 `/` 开头
     * @param content  文件内容
     *
     * @since 2.2.0
     */
    public void upload(String pathname, byte[] content) {
        ossClient.putObject(properties.getBucketName(), pathname, new ByteArrayInputStream(content));
    }

    /**
     * 上传图片
     *
     * @param dir     OSS 目录名
     * @param content 上传内容
     *
     * @since 2.2.0
     */
    public String uploadImageBytes(AliyunOssDir dir, byte[] content) {
        String extname = ExtnameUtil.detectImageFormat(content);
        String filename = dir.getDirname() + "/" + generateRandomString() + "." + extname;
        ossClient.putObject(properties.getBucketName(), filename, new ByteArrayInputStream(content));
        return filename;
    }

    /**
     * 转储第三方资源
     *
     * @param dir OSS 目录名
     * @param url 第三方资源的 URL 地址
     *
     * @return 在 OSS 的存储路径
     *
     * @since 2.2.0
     */
    public String dump(AliyunOssDir dir, String url) {
        RestClient restClient = RestClient
            .builder()
            .build();

        ResponseEntity<byte[]> response = restClient
            .get()
            .uri(url)
            .retrieve()
            .toEntity(byte[].class);

        String extname = Objects
            .requireNonNull(response
                                .getHeaders()
                                .getContentType())
            .getSubtype();

        String filename = dir.getDirname() + "/" + generateRandomString() + "." + extname;

        PutObjectRequest request = new PutObjectRequest(properties.getBucketName(), filename,
                                                        new ByteArrayInputStream(Objects.requireNonNull(response.getBody())));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader("Content-Type", response
            .getHeaders()
            .getContentType()
            .toString());
        request.setMetadata(metadata);
        ossClient.putObject(request);

        return filename;
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
        return properties.getAliasUrl() + "/" + path;
    }

    /**
     * 生成用于客户端直传文件至 OSS 的临时鉴权凭证信息
     *
     * <h2>主要用途
     * <p>客户端可使用该凭证直接将文件上传到 OSS，文件无需经过我方服务器。
     *
     * @see <a href="https://help.aliyun.com/document_detail/91868.html">服务端签名直传</a>
     * @since 1.2.3
     */
    public OssPostCredential generatePostCredential() {
        // 文件在 OSS 中的完整路径
        String filename = AliyunOssDir.CLIENT_DIRECT_TRANSMISSION.getDirname() + "/" + generateRandomString();

        // 凭证有效期结束时间（时间戳）：1小时
        long expireEndTime = System.currentTimeMillis() + Duration
            .ofHours(1L)
            .toMillis();

        Date expiration = new Date(expireEndTime);

        PolicyConditions policyConditions = new PolicyConditions();
        // 指定文件体积（范围）：0 ~ 100MB
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 100L * 1024 * 1024);
        // 指定文件路径（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, filename);
        // 指定存储空间（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, "bucket", properties.getBucketName());

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String policy = BinaryUtil.toBase64String(binaryData);
        String signature = ossClient.calculatePostSignature(postPolicy);

        return OssPostCredential
            .builder()
            .accessKeyId(properties.getAccessKeyId())
            .url(properties.getAliasUrl())
            .key(filename)
            .policy(policy)
            .signature(signature)
            .build();
    }

    /**
     * 获取随机字符串
     *
     * @since 2.2.0
     */
    private String generateRandomString() {
        return IdUtil.simpleUUID();
    }
}
