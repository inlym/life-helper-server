package com.inlym.lifehelper.user.account.service;

import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.mapper.UserMapper;
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
        assertThat(user.getCreateTime()).isBefore(LocalDateTime.now());
        assertThat(Duration.between(user.getCreateTime(), LocalDateTime.now())).isLessThan(Duration.ofSeconds(10L));
        // 默认昵称和头像路径都是空字符串
        assertThat(user.getNickName()).isEmpty();
        assertThat(user.getAvatarPath()).isEmpty();
        // 默认登录次数为 `1`
        assertThat(user.getLoginCounter()).isEqualTo(1L);
        // 再建一个 id 应该比之前的大
        assertThat(userAccountService.createUser()).isGreaterThan(id);
    }
}
