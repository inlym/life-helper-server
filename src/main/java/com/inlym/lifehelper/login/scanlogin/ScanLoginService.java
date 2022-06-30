package com.inlym.lifehelper.login.scanlogin;

import com.inlym.lifehelper.common.auth.core.AuthenticationCredential;
import com.inlym.lifehelper.common.auth.jwt.JwtService;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.login.loginticket.LoginTicketService;
import com.inlym.lifehelper.login.loginticket.entity.LoginTicket;
import com.inlym.lifehelper.login.scanlogin.pojo.LoginTicketVO;
import com.inlym.lifehelper.login.scanlogin.pojo.ScanLoginResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 扫码登录服务
 *
 * <h2>说明
 * <p>在这个服务中处理扫码登录逻辑。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.x.x
 **/
@Service
@RequiredArgsConstructor
public class ScanLoginService {
    private final LoginTicketService loginTicketService;

    private final OssService ossService;

    private final JwtService jwtService;

    /**
     * 获取登录凭证
     *
     * @param ip 发起者的 IP 地址
     *
     * @since 1.3.0
     */
    public LoginTicketVO getCredential(String ip) {
        LoginTicket lc = loginTicketService.create();
        loginTicketService.setIpRegionAsync(lc.getId(), ip);

        LoginTicketVO vo = new LoginTicketVO();
        vo.setId(lc.getId());
        vo.setImageUrl(lc.getUrl());

        return vo;
    }

    public ScanLoginResultVO checkCredential(String id) {
        LoginTicket lc = loginTicketService.getEntity(id);

        if (LoginTicket.Status.CONFIRMED == lc.getStatus()) {
            int userId = lc.getUserId();
            loginTicketService.consume(id);

            AuthenticationCredential ac = jwtService.createAuthenticationCredential(userId);
            ScanLoginResultVO vo = new ScanLoginResultVO();
            BeanUtils.copyProperties(ac, vo);
            vo.setConfirmed(true);
            return vo;
        } else if (LoginTicket.Status.SCANNED == lc.getStatus()) {
            ScanLoginResultVO vo = new ScanLoginResultVO();
            vo.setScanned(true);
            vo.setConfirmed(false);
            return vo;
        } else {
            ScanLoginResultVO vo = new ScanLoginResultVO();
            vo.setScanned(false);
            vo.setConfirmed(false);
            return vo;
        }
    }
}
