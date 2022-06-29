package com.inlym.lifehelper.login.scanlogin.credential;

import com.inlym.lifehelper.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 登录凭证服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class LoginCredentialService {
    private final LoginCredentialRepository repository;

    private final LoginCredentialWxacodeService wxacodeService;

    private final LocationService locationService;

    /**
     * 创建一个新的登录凭证
     *
     * @since 1.3.0
     */
    public LoginCredential create() {
        wxacodeService.batchGenerateIfNeedAsync();

        String id = wxacodeService.getOne();

        LoginCredential lc = new LoginCredential();
        lc.setId(id);
        lc.setUrl(wxacodeService.getUrl(id));
        lc.setStatus(LoginCredential.Status.CREATED);
        lc.setCreateTime(System.currentTimeMillis());

        repository.save(lc);

        return lc;
    }

    /**
     * 获取凭证编码实体
     *
     * @param id 凭证编码 ID
     *
     * @since 1.3.0
     */
    public LoginCredential getEntity(String id) {
        return repository
            .findById(id)
            .orElseThrow(InvalidLoginCredentialException::defaultMessage);
    }

    /**
     * 给登录凭证设置 IP 地址和对应地区信息
     *
     * <h2>说明
     * <p>从流程上来讲，这一步不应该分离出来，而应该在方法 {@link LoginCredentialService#create()} 方法中处理的，分离出来异步处理的优缺点是：
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
        LoginCredential lc = getEntity(id);

        lc.setIp(ip);
        lc.setRegion(locationService.getRoughIpRegion(ip));

        repository.save(lc);
    }

    /**
     * 对登录凭证进行“扫码”操作
     *
     * @param id 登录凭证 ID
     *
     * @since 1.3.0
     */
    public void scan(String id) {
        LoginCredential lc = getEntity(id);
        lc.setStatus(LoginCredential.Status.SCANNED);
        lc.setScanTime(System.currentTimeMillis());

        repository.save(lc);
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
        LoginCredential lc = getEntity(id);
        lc.setStatus(LoginCredential.Status.CONFIRMED);
        lc.setConfirmTime(System.currentTimeMillis());
        lc.setUserId(userId);

        repository.save(lc);
    }

    /**
     * 使用登录凭证（只能使用一次）
     *
     * @param id 登录凭证 ID
     *
     * @since 1.3.0
     */
    public void consume(String id) {
        LoginCredential lc = getEntity(id);
        repository.delete(lc);
    }
}
