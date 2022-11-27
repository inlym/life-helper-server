package com.inlym.lifehelper.common.auth.st.repository;

import com.inlym.lifehelper.common.auth.st.entity.SimpleToken;
import org.springframework.data.repository.CrudRepository;

/**
 * 简易登录令牌存储库
 *
 * <h2>说明
 * <p>该存储库将数据存储在 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/24
 * @since 1.7.0
 **/
public interface SimpleTokenRepository extends CrudRepository<SimpleToken, String> {}
