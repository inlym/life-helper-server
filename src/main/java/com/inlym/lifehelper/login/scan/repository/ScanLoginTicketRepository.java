package com.inlym.lifehelper.login.scan.repository;

import com.inlym.lifehelper.login.scan.entity.ScanLoginTicket;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * 扫码登录凭据存储库
 *
 * <h2>说明
 * <p>该存储库将数据存储在 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/1/5
 * @since 1.9.0
 **/
public interface ScanLoginTicketRepository extends KeyValueRepository<ScanLoginTicket, String> {}
