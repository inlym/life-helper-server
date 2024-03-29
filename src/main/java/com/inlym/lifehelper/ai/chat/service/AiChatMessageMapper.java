package com.inlym.lifehelper.ai.chat.service;

import com.inlym.lifehelper.ai.chat.entity.AiChatMessage;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 智能会话消息存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Mapper
public interface AiChatMessageMapper extends BaseMapper<AiChatMessage> {}
