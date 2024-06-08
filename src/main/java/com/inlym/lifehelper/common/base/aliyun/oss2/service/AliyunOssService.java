package com.inlym.lifehelper.common.base.aliyun.oss2.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.inlym.lifehelper.common.base.aliyun.oss2.config.AliyunOssProperties;
import com.inlym.lifehelper.common.base.aliyun.oss2.constant.AliyunOssDir;
import com.inlym.lifehelper.common.util.ImageUtil;
import com.inlym.lifehelper.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
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
}
