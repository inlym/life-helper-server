package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.inlym.lifehelper.common.base.aliyun.oss.constant.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.model.OssPostCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 阿里云 OSS 综合封装服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @version 2.3.0
 * @since 2024/6/8
 **/
@Service
@RequiredArgsConstructor
public class OssService {
    private final CentralBucketService centralBucketService;

    private final UserUploadBucketService userUploadBucketService;

    /**
     * 生成直传凭据（指定后缀名）
     *
     * @param ext 后缀名，示例值：{@code png}
     *
     * @date 2024/6/8
     * @since 2.3.0
     */
    public OssPostCredential generatePostCredential(String ext) {
        return userUploadBucketService.generatePostCredential(ext);
    }

    /**
     * 转存外部图片
     *
     * @param dir      要保存的目录
     * @param imageUrl 外部图片的 URL 地址
     *
     * @return 保存后的文件路径，示例值：{@code avatar/nUztcRQGBetU.webp}
     * @since 2024/6/8
     */
    public String dumpExternalImage(OssDir dir, String imageUrl) {
        return centralBucketService.dumpImage(dir, imageUrl);
    }

    /**
     * 保存图片
     *
     * @param dir   要保存的目录
     * @param bytes 图片内容
     *
     * @return 保存后的文件路径，示例值：{@code avatar/nUztcRQGBetU.webp}
     * @since 2024/6/8
     */
    public String saveImage(OssDir dir, byte[] bytes) {
        return centralBucketService.saveImage(dir, bytes);
    }

    /**
     * 获取资源的可读（预签名） URL 地址（）
     *
     * @param key 资源在 OSS 的存储路径
     *
     * @return 完整的 URL 地址
     * @since 2024/6/8
     */
    public String getPresignedUrl(String key) {
        return centralBucketService.getPresignedUrl(key);
    }
}
