package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.reminder.entity.Task;
import com.weutil.reminder.event.TaskBelongingChangedEvent;
import com.weutil.reminder.event.TaskCreatedEvent;
import com.weutil.reminder.exception.TaskNotFoundException;
import com.weutil.reminder.mapper.TaskMapper;
import com.weutil.reminder.model.TaskDTO;
import com.weutil.reminder.model.TaskPriority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.weutil.reminder.entity.table.TaskTableDef.TASK;

/**
 * 待办任务服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 新增任务
     *
     * @param entity 实体数据
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public void create(Task entity) {
        taskMapper.insertSelective(entity);

        // 发布创建任务事件
        applicationEventPublisher.publishEvent(new TaskCreatedEvent(entity));
    }

    /**
     * 删除任务
     *
     * @param userId 用户 ID
     * @param taskId 任务 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public void delete(long userId, long taskId) {
        Task task = findById(userId, taskId);
        taskMapper.deleteById(task.getId());

        // 发布删除任务事件
        applicationEventPublisher.publishEvent(task);
    }

    /**
     * 通过 ID 查找任务
     *
     * @param userId 用户 ID
     * @param taskId 任务 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public Task findById(long userId, long taskId) {
        QueryCondition condition = TASK.ID.eq(taskId).and(TASK.USER_ID.eq(userId));
        Task result = taskMapper.selectOneByCondition(condition);

        if (result == null) {
            log.trace("[Reminder] task not found, userId={}, taskId={}", userId, taskId);
            throw new TaskNotFoundException();
        }

        return result;
    }

    /**
     * 修改任务信息
     *
     * @param userId 用户 ID
     * @param taskId 任务 ID
     * @param dto    请求数据
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public void update(long userId, long taskId, TaskDTO dto) {
        // 先查找确认存在，然后再进行下一步操作
        Task task = findById(userId, taskId);

        Task updated = UpdateEntity.of(Task.class, task.getId());
        // 任务名称
        if (dto.getName() != null) {
            updated.setName(dto.getName());
        }
        // 任务描述
        if (dto.getContent() != null) {
            updated.setContent(dto.getContent());
        }
        // 任务优先级
        if (dto.getPriority() != null) {
            updated.setPriority(TaskPriority.fromCode(dto.getPriority()));
        }
        // 截止日期
        if (dto.getDueDate() != null) {
            updated.setDueDate(dto.getDueDate());

            // 截止时间
            if (dto.getDueTime() != null) {
                updated.setDueTime(dto.getDueTime());
            }
        }

        taskMapper.update(updated);
    }

    /**
     * 修改任务归属的项目 ID
     *
     * @param userId          用户 ID
     * @param taskId          任务 ID
     * @param targetProjectId 修改后的项目 ID
     */
    public void changeProjectId(long userId, long taskId, long targetProjectId) {
        // 先查找确认存在，然后再进行下一步操作
        Task task = findById(userId, taskId);

        // 两者不同时才继续
        if (!Objects.equals(task.getProjectId(), targetProjectId)) {
            // 记录修改前的归属项目 ID
            long sourceProjectId = task.getProjectId();

            Task updated = UpdateEntity.of(Task.class, task.getId());
            updated.setProjectId(targetProjectId);
            taskMapper.update(updated);

            applicationEventPublisher.publishEvent(new TaskBelongingChangedEvent(updated, sourceProjectId, targetProjectId));
        }
    }
}
