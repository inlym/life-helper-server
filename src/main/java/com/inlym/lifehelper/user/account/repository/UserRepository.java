package com.inlym.lifehelper.user.account.repository;

import com.inlym.lifehelper.user.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户账户存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 通过 openID 查找用户
     *
     * @param openid 微信小程序的 openID
     *
     * @return 用户实体
     */
    User findByOpenid(String openid);

    /**
     * 通过账户 ID 查找用户
     *
     * @param accountId 账户 ID
     *
     * @return 用户实体
     */
    User findByAccountId(int accountId);
}
