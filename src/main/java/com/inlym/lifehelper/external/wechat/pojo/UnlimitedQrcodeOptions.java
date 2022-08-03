package com.inlym.lifehelper.external.wechat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取不限制的小程序码配置信息
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/4
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html">获取不限制的小程序码</a>
 * @since 1.3.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnlimitedQrcodeOptions {
    /** 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符 */
    private String scene;

    /** 页面，根路径前不要填加 /，不能携带参数 */
    private String page;

    /** 二维码的宽度，单位 px */
    private Integer width = 430;
}
