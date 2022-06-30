package com.inlym.lifehelper.login.loginticket;

import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.login.loginticket.entity.LoginTicket;
import com.inlym.lifehelper.login.loginticket.exception.InvalidLoginTicketException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 登录凭据服务
 *
 * <h2>主要用途
 * <p>服务于 {@link com.inlym.lifehelper.login.loginticket.entity.LoginTicket} 实体的增删改查等操作。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/7/1
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class LoginTicketService {
    private final LoginTicketRepository repository;

    private final LoginTicketWxacodeService wxacodeService;

    private final LocationService locationService;

    /**
     * 创建一个新的登录凭证
     *
     * @since 1.3.0
     */
    public LoginTicket create() {
        wxacodeService.batchGenerateIfNeedAsync();

        String id = wxacodeService.getOne();

        LoginTicket lt = new LoginTicket();
        lt.setId(id);
        lt.setUrl(wxacodeService.getUrl(id));
        lt.setStatus(LoginTicket.Status.CREATED);
        lt.setCreateTime(System.currentTimeMillis());

        repository.save(lt);

        return lt;
    }

    /**
     * 获取凭证编码实体
     *
     * @param id 凭证编码 ID
     *
     * @since 1.3.0
     */
    public LoginTicket getEntity(String id) {
        return repository
            .findById(id)
            .orElseThrow(InvalidLoginTicketException::defaultMessage);
    }

    /**
     * 给登录凭证设置 IP 地址和对应地区信息
     *
     * <h2>说明
     * <p>从流程上来讲，这一步不应该分离出来，而应该在方法 {@link LoginTicketService#create()} 方法中处理的，分离出来异步处理的优缺点是：
     * <p>优点：更快地返回凭证编码，用于 Web 端展示。
     * <p>缺点：小程序端扫码展示信息时，根据 IP 获得地区信息不一定已获取，此时展示为空。（由于中间时间差较大，因此发生的概率较小）
     *
     * @param id 登录凭证 ID
     * @param ip 发起者的 IP 地址
     *
     * @since 1.3.0
     */
    @Async
    public void setIpRegionAsync(String id, String ip) {
        LoginTicket lt = getEntity(id);

        lt.setIp(ip);
        lt.setRegion(locationService.getRoughIpRegion(ip));

        repository.save(lt);
    }

    /**
     * 对登录凭证进行“扫码”操作
     *
     * @param id 登录凭证 ID
     *
     * @since 1.3.0
     */
    public void scan(String id) {
        LoginTicket lt = getEntity(id);
        lt.setStatus(LoginTicket.Status.SCANNED);
        lt.setScanTime(System.currentTimeMillis());

        repository.save(lt);
    }

    /**
     * 对登录凭证进行“确认登录”操作
     *
     * @param id     登录凭证 ID
     * @param userId 操作人用户 ID
     *
     * @since 1.3.0
     */
    public void confirm(String id, int userId) {
        LoginTicket lt = getEntity(id);
        lt.setStatus(LoginTicket.Status.CONFIRMED);
        lt.setConfirmTime(System.currentTimeMillis());
        lt.setUserId(userId);

        repository.save(lt);
    }

    /**
     * 使用登录凭证（只能使用一次）
     *
     * @param id 登录凭证 ID
     *
     * @since 1.3.0
     */
    public void consume(String id) {
        LoginTicket lt = getEntity(id);
        repository.delete(lt);
    }
}
