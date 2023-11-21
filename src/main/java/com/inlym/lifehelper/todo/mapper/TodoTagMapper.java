package com.inlym.lifehelper.todo.mapper;

import com.inlym.lifehelper.todo.entity.TodoTag;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务标签映射器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/11/21
 * @since 2.0.3
 **/
@Mapper
public interface TodoTagMapper extends BaseMapper<TodoTag> {}
