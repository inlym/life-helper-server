package com.weutil.checklist.mapper;

import com.mybatisflex.core.BaseMapper;
import com.weutil.checklist.entity.ChecklistTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 待办任务存储库
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/1
 * @since 2.3.0
 */
@Mapper
public interface ChecklistTaskMapper extends BaseMapper<ChecklistTask> {}
