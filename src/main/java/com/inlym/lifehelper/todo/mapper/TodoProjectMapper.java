package com.inlym.lifehelper.todo.mapper;

import com.inlym.lifehelper.todo.entity.TodoProject;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 待办事项清单映射器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/10/10
 * @since 2.0.3
 **/
@Mapper
public interface TodoProjectMapper extends BaseMapper<TodoProject> {}
