package com.inlym.lifehelper.login.scan;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.extern.wechat.WeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 扫码登录小程序码服务
 *
 * <h2>注意事项
 * <p>当前服务类仅管理用于「扫码登录」使用的小程序码的生成和获取逻辑，不处理任何扫码登录相关逻辑。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/4
 * @since 1.9.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ScanLoginQrcodeService {
    /** 存储在 Redis 中的可用二维码列表使用的键名 */
    private static final String AVAILABLE_QRCODE_LIST = "wechat:qrcode:scan-login:list";

    private final StringRedisTemplate stringRedisTemplate;

    private final WeChatService weChatService;

    private final OssService ossService;

    /**
     * 生成一个小程序码（并上传至阿里云 OSS）
     *
     * @since 1.9.0
     */
    private String generate() {
        // 小程序中用于扫码登录页面的路径
        String page = "pages/scan/login";
        String id = IdUtil.simpleUUID();

        // 向微信服务器获取小程序码
        byte[] qrcode = weChatService.getUnlimitedQrCode(page, id);

        // 上传至阿里云 OSS
        ossService.upload(OssDir.WXACODE + "/" + id, qrcode);

        return id;
    }

    /**
     * 批量生成小程序码（当低于预警值时）
     *
     * @param n 可用小程序码预警值数量
     *
     * @since 1.9.0
     */
    @Async
    public void batchGenerateIfLessThanAsync(int n) {
        Long size = stringRedisTemplate
            .opsForList()
            .size(AVAILABLE_QRCODE_LIST);
        assert size != null;

        // 如果当前数量低于预警值，则批量生成一批新的小程序码
        if (size < n) {
            for (int i = 0; i < n; i++) {
                String id = generate();
                
                // 将 id 添加至可用列表
                stringRedisTemplate
                    .opsForList()
                    .rightPush(AVAILABLE_QRCODE_LIST, id);
            }
        }
    }

    /**
     * 封装用于外部调用的获取小程序码 ID 的方法
     *
     * @since 1.9.0
     */
    public String getOne() {
        String id = stringRedisTemplate
            .opsForList()
            .leftPop(AVAILABLE_QRCODE_LIST);

        if (id != null) {
            return id;
        } else {
            return generate();
        }
    }
}
