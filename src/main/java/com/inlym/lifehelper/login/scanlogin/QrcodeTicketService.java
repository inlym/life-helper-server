package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.external.oss.OssService;
import com.inlym.lifehelper.external.weixin.WeixinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 小程序码凭证编码服务
 *
 * <h2>说明
 * <li>当前包中的 `Qrcode` 均指微信小程序码，不是普通的二维码。
 * <li>这里提到的“凭证编码”就是其他处出现的 `ticket`。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/4/12
 * @since 1.1.0
 **/
@Service
@Slf4j
public class QrcodeTicketService {
    /** 凭证编码列表的 Redis 键名 */
    public static final String TICKET_LIST_KEY = "scan_login:ticket_list";

    private final WeixinService weixinService;

    private final StringRedisTemplate stringRedisTemplate;

    private final OssService ossService;

    public QrcodeTicketService(WeixinService weixinService, StringRedisTemplate stringRedisTemplate, OssService ossService) {
        this.weixinService = weixinService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.ossService = ossService;
    }

    /**
     * 根据凭证编号获取其在 OSS 中的存储路径
     *
     * @param ticket 凭证编号
     *
     * @since 1.1.0
     */
    public String getPathname(String ticket) {
        return OssService.WXACODE_DIR + "/" + ticket + ".png";
    }

    /**
     * 生成一个凭证编码
     *
     * <h2>主要流程
     * <li>使用去掉短横线的 UUID 作为凭证编码
     * <li>生成对应参数的小程序码
     * <li>将小程序码上传到 OSS
     * <li>将凭证编码存入 Redis 的对应列表，用于后续获取
     *
     * <h2>备注
     * <p>整个流程调用链较长，耗时较大，在本地开发环境实测整个方法的总耗时在 200ms ~ 600ms 之间。
     *
     * @since 1.1.0
     */
    public String createTicket() {
        String page = "pages/scan/login";
        String ticket = UUID
            .randomUUID()
            .toString()
            .replaceAll("-", "");
        int width = 430;

        // 请求微信服务器获取小程序码
        byte[] bytes = weixinService.generateWxacode(page, ticket, width);

        // 将图片上传至 OSS
        ossService.upload(getPathname(ticket), bytes);

        // 存储到 Redis
        stringRedisTemplate
            .opsForList()
            .leftPush(TICKET_LIST_KEY, ticket);

        return ticket;
    }

    /**
     * 批量生成指定个数的凭证编码（异步）
     *
     * <h2>主要用途
     * <p>同步方法耗时较长，在请求中调用，整个请求时长会很久，因此在特定情况下，使用异步方法提前生成凭证编号，后续直接从列表中获取即可。
     *
     * <h2>使用说明
     * <p>当前方法可在任意处调用，内部处理了是否执行了逻辑。
     *
     * @param fewNum    较低值，剩余量低于该值则执行方法批量生成若干个凭证编码
     * @param createNum 生成个数
     *
     * @since 1.1.0
     */
    @Async
    public void createTicketsWhenFewAsync(int fewNum, int createNum) {
        Long rest = stringRedisTemplate
            .opsForList()
            .size(TICKET_LIST_KEY);
        assert rest != null;

        if (rest <= fewNum) {
            for (int i = 0; i < createNum; i++) {
                createTicket();
            }
        }
    }

    /**
     * 获取一个可用的码凭证编号
     *
     * @since 1.1.0
     */
    public String getTicket() {
        String ticket = stringRedisTemplate
            .opsForList()
            .rightPop(TICKET_LIST_KEY);

        if (ticket != null) {
            return ticket;
        }

        // 当列表为空时，实时生成一个再返回
        return createTicket();
    }
}
