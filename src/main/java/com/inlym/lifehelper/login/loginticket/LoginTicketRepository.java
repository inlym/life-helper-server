package com.inlym.lifehelper.login.loginticket;

import com.inlym.lifehelper.login.loginticket.entity.LoginTicket;
import org.springframework.data.repository.CrudRepository;

/**
 * 登录凭据存储库
 *
 * <h2>说明
 * <p>数据仅存储在 Redis 中，未在 MySQL 中落库。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/7/1
 * @since 1.3.0
 **/
public interface LoginTicketRepository extends CrudRepository<LoginTicket, String> {}
