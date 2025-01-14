package com.weutil.oss.service;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.weutil.common.util.RandomStringUtil;
import com.weutil.oss.config.OssProperties;
import com.weutil.oss.model.GeneratingPostCredentialOptions;
import com.weutil.oss.model.OssDir;
import com.weutil.oss.model.OssPostCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

/**
 * OSS 服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class OssService {
    private final OssProperties ossProperties;
    private final OSS ossClient;
    private final OSS ossClientForPresigning;

    /**
     * 内部复制对象（目前仅作用于图片文件）
     *
     * <h3>使用说明
     * <p>主要从“用户上传目录（upload）”复制到其他目录。
     *
     * @param sourceKey 源地址
     * @param targetDir 目标目录
     *
     * @return 复制后的完整文件地址，示例值 {@code avatar/r7OPIjuso1rR.png}
     */
    public String copyInternal(String sourceKey, OssDir targetDir) {
        // 源存储桶和目标存储桶是同一个
        String bucketName = ossProperties.getBucketName();

        // 获取源对象元数据
        ObjectMetadata meta = ossClient.getObjectMetadata(bucketName, sourceKey);
        // 获取后缀名
        String extension = MediaType.parseMediaType(meta.getContentType()).getSubtype();
        // 生成目标文件路径
        String targetKey = targetDir.getDirname() + "/" + RandomStringUtil.generate(12) + "." + extension;

        CopyObjectRequest request = new CopyObjectRequest(bucketName, sourceKey, bucketName, targetKey);
        ossClient.copyObject(request);

        return targetKey;
    }

    /**
     * 将 OSS 内部图片转储为 WebP 格式
     *
     * @param sourceKey 源地址
     * @param targetDir 目标目录
     *
     * @return 复制后的完整文件地址，示例值 {@code avatar/r7OPIjuso1rR.png}
     * @date 2025/01/15
     * @see <a href="https://help.aliyun.com/zh/oss/user-guide/convert-image-formats-2">格式转换</a>
     * @since 3.0.0
     */
    public String dumpAsWebpInternal(String sourceKey, OssDir targetDir) {
        String bucketName = ossProperties.getBucketName();
        String format = "image/format,webp";
        String targetKey = targetDir.getDirname() + "/" + RandomStringUtil.generate(12) + ".webp";

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, sourceKey);
        getObjectRequest.setProcess(format);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, targetKey, ossClient.getObject(getObjectRequest).getObjectContent());
        ossClient.putObject(putObjectRequest);

        return targetKey;
    }

    /**
     * 生成直传凭据
     *
     * @param options 生成环节使用的配置项
     *
     * @date 2024/6/8
     * @see <a href="https://help.aliyun.com/zh/oss/use-cases/obtain-signature-information-from-the-server-and-upload-data-to-oss">服务端签名直传</a>
     * @since 2.3.0
     */
    public OssPostCredential generatePostCredential(GeneratingPostCredentialOptions options) {
        String dateStr = LocalDate.now().toString().replaceAll("-", "");
        // 文件名，示例值：`upload/20240715/r7OPIjuso1rR.png`
        String key = OssDir.UPLOAD.getDirname() + "/" + dateStr + "/" + RandomStringUtil.generate(12) + "." + options.getExtension();
        // 到期时间
        Date expiration = new Date(System.currentTimeMillis() + options.getDuration().toMillis());

        PolicyConditions conditions = new PolicyConditions();
        // 指定文件体积（范围）
        conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, options.getSizeMB() * 1024 * 1024);
        // 指定文件路径（完全匹配）
        conditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, key);
        // 指定存储空间（完全匹配）
        conditions.addConditionItem(MatchMode.Exact, "bucket", ossProperties.getBucketName());

        String postPolicy = ossClient.generatePostPolicy(expiration, conditions);
        String policy = BinaryUtil.toBase64String(postPolicy.getBytes(StandardCharsets.UTF_8));
        String signature = ossClient.calculatePostSignature(postPolicy);

        return OssPostCredential
            .builder()
            .accessKeyId(ossProperties.getAccessKeyId())
            .url("https://" + ossProperties.getCustomDomain())
            .key(key)
            .policy(policy)
            .signature(signature)
            .build();
    }

    /**
     * 获取资源的可读（预签名） URL 地址
     *
     * <h3>说明
     * <p>由于存储空间（bucket）是私有的，因此需要通过以下方法生成用户可直接访问的链接。
     *
     * @param key 资源在 OSS 的存储路径
     *
     * @return 完整的 URL 地址
     * @see <a href="https://help.aliyun.com/zh/oss/user-guide/how-to-obtain-the-url-of-a-single-object-or-the-urls-of-multiple-objects">使用文件URL分享文件</a>
     * @since 2024/6/8
     */
    public String getPresignedUrl(String key) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossProperties.getBucketName(), key, HttpMethod.GET);
        // 设置过期时间，目前默认：10天
        request.setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(10L).toMillis()));

        return ossClientForPresigning.generatePresignedUrl(request).toString();
    }
}
