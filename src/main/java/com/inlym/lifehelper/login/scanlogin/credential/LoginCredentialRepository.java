package com.inlym.lifehelper.login.scanlogin.credential;

import org.springframework.data.repository.CrudRepository;

/**
 * 登录凭证存储库（Redis）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/27
 * @since 1.3.0
 **/
public interface LoginCredentialRepository extends CrudRepository<LoginCredential, String> {}
