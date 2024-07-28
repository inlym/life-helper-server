package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.weutil.reminder.mapper.ProjectMapper;
import com.weutil.reminder.mapper.TagMapper;
import com.weutil.reminder.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.weutil.reminder.entity.table.TaskTableDef.TASK;

/**
 * 共用计数服务
 *
 * <h2>主要用途
 * <p>在各自“服务”内，需要计算其他实体的数量，引用对方服务，可能造成依赖的“循环引用”，因此将“计数”事项剥离出来，都统一放置在当前服务中处理。
 *
 * <h2>附加说明
 * <p>为方便处理，在当前服务中直接调用了 {@code Mapper}，该操作不符合规范，特此说明。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SharedCountingService {
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final TagMapper tagMapper;

    /**
     * 计算指定项目未完成的任务数
     *
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    public long countUncompletedTasksForProject(long projectId) {
        QueryCondition condition = TASK.PROJECT_ID.eq(projectId).and(TASK.COMPLETE_TIME.isNull());
        return taskMapper.selectCountByCondition(condition);
    }
}
