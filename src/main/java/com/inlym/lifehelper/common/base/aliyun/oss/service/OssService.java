package com.inlym.lifehelper.common.base.aliyun.oss.service;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.inlym.lifehelper.common.base.aliyun.oss.config.AliyunOssProperties;
import com.inlym.lifehelper.common.base.aliyun.oss.model.GeneratePostCredentialOptions;
import com.inlym.lifehelper.common.base.aliyun.oss.model.OssPostCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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
     * 转储文件
     *
     * <h2>说明
     * <li>实测客户端将资源直传至 OSS，此时立即访问资源时，会告知资源不存在，然后过一会就正常了，因此此处增加重试机制。
     *
     * @param dirname 转储的目录（注意不要以 `/` 开头）
     * @param url     资源文件的 URL 地址
     *
     * @return 资源在 OSS 中的路径
     *
     * @since 1.0.0
     */
    @Retryable
    public String dump(String dirname, String url) {
        String bucketName = properties.getBucketName();

        // 文件路径
        String pathname = dirname + "/" + IdUtil.simpleUUID();

        RestClient restClient = RestClient
            .builder()
            .build();

        ResponseEntity<byte[]> response = restClient
            .get()
            .uri(url)
            .retrieve()
            .toEntity(byte[].class);

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
    public OssPostCredential generatePostCredential(GeneratePostCredentialOptions options) {
        // 文件在 OSS 中的完整路径
        String pathname = options.getDirname() + "/" + IdUtil.simpleUUID();

        // 凭证有效期结束时间（时间戳）
        long expireEndTime = System.currentTimeMillis() + options
            .getTtl()
            .toMillis();
        Date expiration = new Date(expireEndTime);

        PolicyConditions policyConditions = new PolicyConditions();
        // 指定文件体积（范围）
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, options.getMaxSize());
        // 指定文件路径（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, pathname);
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
            .key(pathname)
            .policy(policy)
            .signature(signature)
            .build();
    }
}
