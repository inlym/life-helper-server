package com.inlym.lifehelper.login.scan;

import com.inlym.lifehelper.login.scan.repository.ScanLoginTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 扫码登录凭据服务
 *
 * <h2>主要用途
 * <p>管理「扫码登录凭据」的创建、扫码、确认、使用、销毁等生命周期。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ScanLoginTicketService {
    private final ScanLoginTicketRepository repository;
}
