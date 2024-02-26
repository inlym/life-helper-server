package com.inlym.lifehelper.login.qrcode.service;

import com.inlym.lifehelper.login.qrcode.entity.LoginTicket;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * 扫码登录凭据村出库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
public interface LoginTicketRepository extends KeyValueRepository<LoginTicket, String> {}
