package com.inlym.lifehelper.ai.service;

import com.inlym.lifehelper.ai.entity.AiChatMessage;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 智能会话消息村出库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/25
 * @since 2.2.0
 **/
@Mapper
public interface AiChatMessageMapper extends BaseMapper<AiChatMessage> {}
