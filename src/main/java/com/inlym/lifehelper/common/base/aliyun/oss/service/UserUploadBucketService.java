package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PolicyConditions;
import com.inlym.lifehelper.common.base.aliyun.oss.config.UserUploadBucketProperties;
import com.inlym.lifehelper.common.base.aliyun.oss.model.GeneratePostCredentialOptions;
import com.inlym.lifehelper.common.base.aliyun.oss.model.OssPostCredential;
import com.inlym.lifehelper.common.util.StringUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

/**
 * 用户直传专用存储空间服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class UserUploadBucketService {
    private final UserUploadBucketProperties properties;

    private final OSS userUploadBucketClient;

    /**
     * 生成直传凭据（指定后缀名，其余自动）
     *
     * @param ext 后缀名，示例值：{@code png}
     *
     * @date 2024/6/8
     * @since 2.3.0
     */
    public OssPostCredential generatePostCredential(@NonNull String ext) {
        String dateStr = LocalDate.now().toString().replaceAll("-", "");
        String key = dateStr + "/" + StringUtil.generateRandomString(12) + "." + ext;
        // 默认有效期：2小时
        Duration duration = Duration.ofHours(2);
        // 默认文件最大体积：100MB
        long sizeMB = 100L;

        return generatePostCredential(GeneratePostCredentialOptions
                                              .builder()
                                              .key(key)
                                              .duration(duration)
                                              .sizeMB(sizeMB)
                                              .build());
    }

    /**
     * 生成直传凭据
     *
     * @param options 生成环节使用的配置项
     *
     * @date 2024/6/8
     * @see
     * <a href="https://help.aliyun.com/zh/oss/use-cases/obtain-signature-information-from-the-server-and-upload-data-to-oss">服务端签名直传</a>
     * @since 2.3.0
     */
    public OssPostCredential generatePostCredential(GeneratePostCredentialOptions options) {
        Date expiration = new Date(System.currentTimeMillis() + options.getDuration().toMillis());

        PolicyConditions conditions = new PolicyConditions();
        // 指定文件体积（范围）
        conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, options.getSizeMB() * 1024 * 1024);
        // 指定文件路径（完全匹配）
        conditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, options.getKey());
        // 指定存储空间（完全匹配）
        conditions.addConditionItem(MatchMode.Exact, "bucket", properties.getBucketName());

        String postPolicy = userUploadBucketClient.generatePostPolicy(expiration, conditions);
        String policy = BinaryUtil.toBase64String(postPolicy.getBytes(StandardCharsets.UTF_8));
        String signature = userUploadBucketClient.calculatePostSignature(postPolicy);

        return OssPostCredential
                .builder()
                .accessKeyId(properties.getAccessKeyId())
                .url("https://" + properties.getCustomDomain())
                .key(options.getKey())
                .policy(policy)
                .signature(signature)
                .build();
    }

    /**
     * 获取在 OSS 中的文件
     *
     * @param key 文件在 OSS 的存储路径，示例值：{@code 20240608/r7OPIjuso1rR.png}
     *
     * @date 2024/6/8
     * @since 2.3.0
     */
    public OSSObject getObject(String key) {
        return userUploadBucketClient.getObject(properties.getBucketName(), key);
    }
}
