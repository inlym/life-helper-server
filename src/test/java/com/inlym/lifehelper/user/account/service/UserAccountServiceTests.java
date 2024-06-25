package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.account.user.entity.User;
import com.inlym.lifehelper.account.user.mapper.UserMapper;
import com.inlym.lifehelper.account.user.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserAccountServiceTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAccountService userAccountService;

    @Test
    void testCreateUser() {
        long id = userAccountService.createUser();

        // 新生成的用户 ID 要求大于 0
        assertThat(id).isGreaterThan(0L);
        // 通过 {@code id} 查找记录，应该存在
        User user = userMapper.selectOneById(id);
        assertThat(user).isNotNull();
        // {@code id} 字段值相同
        assertThat(user.getId()).isEqualTo(id);
        // 创建时间应该是“刚刚”（设定10秒内）
        assertThat(Duration.between(user.getCreateTime(), LocalDateTime.now())).isLessThan(Duration.ofSeconds(10L));
        // 默认昵称和头像路径都有默认值
        assertThat(user.getNickName()).isNotEmpty();
        assertThat(user.getAvatarPath()).isNotEmpty();
        // 默认登录次数为 `1`
        assertThat(user.getLoginCounter()).isEqualTo(0L);
        // 再建一个 id 应该比之前的大
        assertThat(userAccountService.createUser()).isGreaterThan(id);
    }
}
