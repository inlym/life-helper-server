package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.inlym.lifehelper.common.base.aliyun.oss.config.AliyunOssProperties;
import com.inlym.lifehelper.common.base.aliyun.oss.constant.AliyunOssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.model.OssPostCredential;
import com.inlym.lifehelper.common.util.ImageUtil;
import com.inlym.lifehelper.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
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
public class AliyunOssService {
    private final OSS ossClient;

    private final AliyunOssProperties properties;

    /**
     * 上传图片
     *
     * @param dir     OSS 目录名
     * @param content 上传内容
     *
     * @return 资源在 OSS 存储的完整路径（不带 "/"）
     * @since 2.2.0
     */
    public String uploadImageBytes(AliyunOssDir dir, byte[] content) {
        String extname = ImageUtil.detectFormat(content);
        String pathname = dir.getDirname() + "/" + StringUtil.generateRandomString(12) + "." + extname;
        ossClient.putObject(properties.getBucketName(), pathname, new ByteArrayInputStream(content));
        return pathname;
    }

    /**
     * 转储第三方资源
     *
     * @param dir OSS 目录名
     * @param url 第三方资源的 URL 地址
     *
     * @return 在 OSS 的存储路径
     * @since 2.2.0
     */
    public String dump(AliyunOssDir dir, String url) {
        RestClient restClient = RestClient.builder().build();

        ResponseEntity<byte[]> response = restClient.get().uri(url).retrieve().toEntity(byte[].class);

        String extname = ImageUtil.getExtensionFromMediaType(Objects
                                                                     .requireNonNull(response
                                                                                             .getHeaders()
                                                                                             .getContentType())
                                                                     .toString());

        String filename = dir.getDirname() + "/" + StringUtil.generateRandomString(12) + "." + extname;

        PutObjectRequest request = new PutObjectRequest(properties.getBucketName(), filename,
                                                        new ByteArrayInputStream(Objects.requireNonNull(response.getBody())));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader("Content-Type", response.getHeaders().getContentType().toString());
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
     * <p>客户端可使用该凭证直接将文件上传到 OSS，文件无需经过我方项目服务器。
     *
     * @param ext 后缀名，例如 `png`, `jpg`, ...
     *
     * @see <a href="https://help.aliyun.com/document_detail/91868.html">服务端签名直传</a>
     * @since 1.2.3
     */
    public OssPostCredential generatePostCredential(String ext) {
        String date = LocalDate.now().toString().replaceAll("-", "");

        // 目录名
        String dirname = AliyunOssDir.CLIENT_DIRECT_TRANSMISSION.getDirname() + "/" + date;
        // 文件名
        String filename = StringUtil.generateRandomString(12) + "." + ext;
        // 构建文件在 OSS 中的完整路径
        String path = dirname + "/" + filename;

        // 凭证有效期结束时间（时间戳）：1小时
        long expireEndTime = System.currentTimeMillis() + Duration.ofHours(1L).toMillis();
        Date expiration = new Date(expireEndTime);

        PolicyConditions policyConditions = new PolicyConditions();
        // 指定文件体积（范围）：0 ~ 100MB
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 100L * 1024 * 1024);
        // 指定文件路径（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, path);
        // 指定存储空间（完全匹配）
        policyConditions.addConditionItem(MatchMode.Exact, "bucket", properties.getBucketName());

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        String policy = BinaryUtil.toBase64String(postPolicy.getBytes(StandardCharsets.UTF_8));
        String signature = ossClient.calculatePostSignature(postPolicy);

        return OssPostCredential
                .builder()
                .accessKeyId(properties.getAccessKeyId())
                .url(properties.getAliasUrl())
                .key(path)
                .policy(policy)
                .signature(signature)
                .build();
    }
}
