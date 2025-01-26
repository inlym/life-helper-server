package com.weutil.todolist.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.todolist.entity.TodolistTask;
import com.weutil.todolist.event.*;
import com.weutil.todolist.exception.TodolistProjectNotFoundException;
import com.weutil.todolist.exception.TodolistTaskNotFoundException;
import com.weutil.todolist.mapper.ReminderProjectMapper;
import com.weutil.todolist.mapper.ReminderTaskMapper;
import com.weutil.todolist.model.TodolistTaskOperation;
import com.weutil.todolist.model.UpdateTodolistTaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.weutil.todolist.entity.table.ReminderProjectTableDef.REMINDER_PROJECT;
import static com.weutil.todolist.entity.table.ReminderTaskTableDef.REMINDER_TASK;

/**
 * 待办任务服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/23
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TodolistTaskService {
    private final ReminderProjectMapper reminderProjectMapper;
    private final ReminderTaskMapper reminderTaskMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 创建待办任务
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     * @param name      待办任务名称
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public TodolistTask create(long userId, long projectId, String name) {
        // 检查待办项目所属权
        checkProjectOwnership(userId, projectId);

        TodolistTask inserted = TodolistTask.builder().userId(userId).projectId(projectId).name(name).build();
        reminderTaskMapper.insertSelective(inserted);

        TodolistTask entity = getOrThrowById(userId, inserted.getId());

        // 发布待办任务创建事件
        applicationEventPublisher.publishEvent(new TodolistTaskCreatedEvent(entity));

        return entity;
    }

    /**
     * 检查待办项目所属权
     *
     * <h3>开发备注
     * <p>此处实际上可以直接调用 {@link TodolistProjectService} 的 {@code getOrThrowById} 方法, 为避免出现循环依赖，此处单独写一个。
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    private void checkProjectOwnership(long userId, long projectId) {
        // [projectId=0] 情况，无需检查
        if (projectId > 0) {
            QueryCondition condition = REMINDER_PROJECT.USER_ID.eq(userId).and(REMINDER_PROJECT.ID.eq(projectId));
            if (reminderProjectMapper.selectCountByCondition(condition) != 1) {
                throw new TodolistProjectNotFoundException(projectId, userId);
            }
        }
    }

    /**
     * 根据 ID 获取实体对象，如果未找到（包含不所属于对应用户）则抛出异常
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public TodolistTask getOrThrowById(long userId, long taskId) {
        TodolistTask entity = reminderTaskMapper.selectOneById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new TodolistTaskNotFoundException(taskId, userId);
    }

    /**
     * 根据 ID 获取实体对象（包含关联数据），如果未找到（包含不所属于对应用户）则抛出异常
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public TodolistTask getWithRelationsOrThrowById(long userId, long taskId) {
        TodolistTask entity = reminderTaskMapper.selectOneWithRelationsById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new TodolistTaskNotFoundException(taskId, userId);
    }

    /**
     * 根据项目 ID 获取任务列表
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/12/27
     * @since 3.0.0
     */
    public List<TodolistTask> listByProjectId(long userId, long projectId) {
        checkProjectOwnership(userId, projectId);

        QueryCondition condition = REMINDER_TASK.USER_ID.eq(userId).and(REMINDER_TASK.PROJECT_ID.eq(projectId));
        return reminderTaskMapper.selectListByCondition(condition);
    }

    /**
     * 根据请求数据更新实体
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     * @param dto    请求数据
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    public void updateWithDTO(long userId, long taskId, UpdateTodolistTaskDTO dto) {
        TodolistTask entity = getOrThrowById(userId, taskId);

        if (dto.getOperation() != null) {
            TodolistTaskOperation operation = dto.getOperation();
            if (operation == TodolistTaskOperation.COMPLETE) {
                complete(entity);
            } else if (operation == TodolistTaskOperation.UNCOMPLETE) {
                uncomplete(entity);
            } else if (operation == TodolistTaskOperation.CLEAR_DUE_DATETIME) {
                clearDueDateTime(entity);
            }
        } else {
            TodolistTask updated = UpdateEntity.of(TodolistTask.class, entity.getId());

            if (dto.getName() != null && !dto.getName().equals(entity.getName())) {
                updated.setName(dto.getName());
            }
            if (dto.getProjectId() != null && !dto.getProjectId().equals(entity.getProjectId())) {
                move(entity, dto.getProjectId());
            }
            if (dto.getContent() != null && !dto.getContent().equals(entity.getContent())) {
                updated.setContent(dto.getContent());
            }
            if (dto.getDueDate() != null && dto.getDueTime() != null) {
                updated.setDueDate(dto.getDueDate());
                updated.setDueTime(dto.getDueTime());
                updated.setDueDateTime(LocalDateTime.of(dto.getDueDate(), dto.getDueTime()));
            }
            if (dto.getDueDate() != null && dto.getDueTime() == null) {
                updated.setDueDate(dto.getDueDate());
                updated.setDueTime(null);
                updated.setDueDateTime(LocalDateTime.of(dto.getDueDate(), LocalTime.MAX));
            }
            if (dto.getPriority() != null) {
                updated.setPriority(dto.getPriority());
            }

            reminderTaskMapper.update(updated);
        }
    }

    /**
     * 标记任务为已完成
     *
     * @param entity 从数据库查询的实体
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    public void complete(TodolistTask entity) {
        if (entity.getCompleteTime() == null) {
            TodolistTask updated = TodolistTask.builder().id(entity.getId()).completeTime(LocalDateTime.now()).build();

            reminderTaskMapper.update(updated);
            applicationEventPublisher.publishEvent(new TodolistTaskCompletedEvent(entity));
        }
    }

    /**
     * 标记任务为未完成
     *
     * @param entity 从数据库查询的实体
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    public void uncomplete(TodolistTask entity) {
        if (entity.getCompleteTime() != null) {
            TodolistTask updated = UpdateEntity.of(TodolistTask.class, entity.getId());
            updated.setCompleteTime(null);

            reminderTaskMapper.update(updated);
            applicationEventPublisher.publishEvent(new TodolistTaskUncompletedEvent(entity));
        }
    }

    /**
     * 清除截止期限
     *
     * @param entity 从数据库查询的实体
     *
     * @date 2025/01/09
     * @since 3.0.0
     */
    public void clearDueDateTime(TodolistTask entity) {
        if (entity.getDueDateTime() != null || entity.getDueDate() != null || entity.getDueTime() != null) {
            TodolistTask updated = UpdateEntity.of(TodolistTask.class, entity.getId());
            updated.setDueDateTime(null);
            updated.setDueDate(null);
            updated.setDueTime(null);

            reminderTaskMapper.update(updated);
        }
    }

    /**
     * 移动任务所属的项目
     *
     * @param entity          从数据库查询的实体
     * @param targetProjectId 目标项目 ID
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    public void move(TodolistTask entity, long targetProjectId) {
        checkProjectOwnership(entity.getUserId(), targetProjectId);

        if (!entity.getProjectId().equals(targetProjectId)) {
            TodolistTask updated = TodolistTask.builder().id(entity.getId()).projectId(targetProjectId).build();
            reminderTaskMapper.update(updated);

            applicationEventPublisher.publishEvent(new TodolistTaskMovedEvent(entity, entity.getProjectId(), targetProjectId));
        }
    }

    /**
     * 删除一条待办任务
     *
     * @param userId 用户 ID
     * @param taskId 待办任务 ID
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    public void delete(long userId, long taskId) {
        TodolistTask entity = getOrThrowById(userId, taskId);
        reminderTaskMapper.deleteById(entity.getId());

        applicationEventPublisher.publishEvent(new TodolistTaskDeletedEvent(entity));
    }
}
