package com.inlym.lifehelper.login.scan;

import com.inlym.lifehelper.common.auth.core.SecurityToken;
import com.inlym.lifehelper.common.auth.simpletoken.SimpleTokenService;
import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.common.exception.UnpredictableException;
import com.inlym.lifehelper.login.scan.constant.ScanLoginTicketStatus;
import com.inlym.lifehelper.login.scan.entity.ScanLoginTicket;
import com.inlym.lifehelper.login.scan.pojo.ScanLoginResultVO;
import com.inlym.lifehelper.login.scan.pojo.ScanLoginTicketVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 扫码登录服务
 *
 * <h2>主要用途
 * <p>管理「扫码登录」流程。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/6
 * @since 1.9.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ScanLoginService {
    private final ScanLoginTicketService scanLoginTicketService;

    private final OssService ossService;

    private final SimpleTokenService simpleTokenService;

    /**
     * 获取用于扫码登录操作的小程序码信息
     *
     * @since 1.9.0
     */
    public ScanLoginTicketVO getQrcode(String ip) {
        ScanLoginTicket ticket = scanLoginTicketService.create(ip);

        // 异步处理去获取该 IP 地址的所在区域
        scanLoginTicketService.setRegionAsync(ticket.getId());

        return ScanLoginTicketVO
            .builder()
            .id(ticket.getId())
            .url(ossService.concatUrl(OssDir.WXACODE + "/" + ticket.getId()))
            .build();
    }

    /**
     * 获取扫码登录结果
     *
     * <h2>安全校验
     * <p>会检测发起「获取小程序码」和「检查登录状态」的客户端为同一 IP 地址。
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginResultVO getScanLoginResult(String id, String ip) {
        ScanLoginTicket ticket = scanLoginTicketService.getOrElseThrow(id, ip);

        if (ticket.getStatus() == ScanLoginTicketStatus.CREATED) {
            return ScanLoginResultVO
                .builder()
                .scanned(false)
                .logined(false)
                .build();
        } else if (ticket.getStatus() == ScanLoginTicketStatus.SCANNED) {
            return ScanLoginResultVO
                .builder()
                .scanned(true)
                .logined(false)
                .build();
        } else if (ticket.getStatus() == ScanLoginTicketStatus.CONFIRMED) {
            SecurityToken securityToken = simpleTokenService.generateSecurityToken(ticket.getUserId());
            scanLoginTicketService.destroy(ticket.getId());
            return ScanLoginResultVO
                .builder()
                .scanned(true)
                .logined(true)
                .securityToken(securityToken)
                .build();
        } else {
            throw new UnpredictableException("扫码登录凭据异常，" + ticket);
        }
    }

    /**
     * /** 「扫码」操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginTicketVO scan(String id) {
        ScanLoginTicket ticket = scanLoginTicketService.scan(id);
        return ScanLoginTicketVO
            .builder()
            .id(ticket.getId())
            .ip(ticket.getIp())
            .region(ticket.getRegion())
            .build();
    }

    /**
     * 「确认登录」操作
     *
     * @param id 扫码登录凭据 ID
     *
     * @since 1.9.0
     */
    public ScanLoginTicketVO confirm(String id, int userId) {
        ScanLoginTicket ticket = scanLoginTicketService.confirm(id, userId);
        return ScanLoginTicketVO
            .builder()
            .id(ticket.getId())
            .build();
    }
}
