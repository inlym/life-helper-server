package com.inlym.lifehelper.common.base.aliyun.oss;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.inlym.lifehelper.common.base.aliyun.oss.pojo.GeneratePostCredentialOptions;
import com.inlym.lifehelper.common.base.aliyun.oss.pojo.OssPostCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

/**
 * OSS 服务类
 *
 * <h2>注意事项
 * <li>当前只用到一个 OSS 储存空间（bucket），使用不同目录存放不同来源的资源。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-02-12
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class OssService {
    private final OSS ossClient;

    private final OssProperties ossProperties;

    private final RestTemplate restTemplate;

    /**
     * 上传文件
     *
     * @param pathname 文件路径，注意不要以 `/` 开头
     * @param content  文件内容
     *
     * @since 1.0.0
     */
    public void upload(String pathname, byte[] content) {
        ossClient.putObject(ossProperties.getBucketName(), pathname, new ByteArrayInputStream(content));
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
        String pathname = dirname + "/" + IdUtil.simpleUUID();

        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), pathname, new ByteArrayInputStream(Objects.requireNonNull(response.getBody())));
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
        policyConditions.addConditionItem(MatchMode.Exact, "bucket", ossProperties.getBucketName());

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String policy = BinaryUtil.toBase64String(binaryData);
        String signature = ossClient.calculatePostSignature(postPolicy);

        return OssPostCredential
            .builder()
            .accessKeyId(ossProperties.getAccessKeyId())
            .url(ossProperties.getAliasUrl())
            .key(pathname)
            .policy(policy)
            .signature(signature)
            .build();
    }
}
