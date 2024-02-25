package com.inlym.lifehelper.login.qrcode2.service;

import com.inlym.lifehelper.login.qrcode2.entity.QrCodeTicket;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * 二维码凭据存储库
 *
 * <h2>说明
 * <p>存储在 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/15
 * @since 2.0.0
 **/
public interface QrCodeTicketRepository extends KeyValueRepository<QrCodeTicket, String> {}
