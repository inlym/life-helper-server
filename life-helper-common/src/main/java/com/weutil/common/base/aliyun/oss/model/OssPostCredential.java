package com.weutil.common.base.aliyun.oss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云 OSS 直传凭据
 *
 * <h2>主要用途
 * <p>用于客户端将资源直传阿里云的 OSS 提供鉴权凭证，客户端获取凭据后对应字段填充至需要的位置即可。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/8
 * @see <a href="https://help.aliyun.com/zh/oss/use-cases/obtain-signature-information-from-the-server-and-upload-data-to-oss">服务端签名直传</a>
 * @since 2.3.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssPostCredential {
    /** 客户端使用时参数改为 `OSSAccessKeyId` */
    private String accessKeyId;

    /** 上传地址，即绑定的域名，示例值：{@code https://upload.weutil.com} */
    private String url;

    /** 上传至 OSS 后的文件路径，示例值：{@code 20240608/r7OPIjuso1rR.png} */
    private String key;

    /** 用户表单上传的策略，是经过 Base64 编码过的字符串 */
    private String policy;

    /** 对 policy 签名后的字符串 */
    private String signature;
}
