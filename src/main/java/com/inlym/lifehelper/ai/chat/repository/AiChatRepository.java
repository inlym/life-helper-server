package com.inlym.lifehelper.ai.chat.repository;

import com.inlym.lifehelper.ai.chat.entity.AiChat;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * 智能会话对象存储库
 *
 * <h2>说明
 * <p>该存储库将数据存储在 Redis 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/3/27
 * @since 1.9.6
 **/
public interface AiChatRepository extends KeyValueRepository<AiChat, String> {}
