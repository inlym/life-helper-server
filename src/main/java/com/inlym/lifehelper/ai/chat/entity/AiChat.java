package com.inlym.lifehelper.ai.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 智能会话对象实体
 *
 * <h2>主要用途
 * <p>封装智能会话对象
 *
 * <h2>缓存有效期
 * <p>10天
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "database:ai-chat", timeToLive = 10 * 24 * 60 * 60)
public class AiChat {
    /** 会话 ID */
    @Id
    private String id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最后一条消息的时间 */
    private LocalDateTime updateTime;

    /** 消息列表 */
    private List<AiChatMessage> messages;
}
