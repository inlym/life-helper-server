package com.weutil.todo.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.todo.entity.TodoTask;
import com.weutil.todo.event.*;
import com.weutil.todo.exception.TodoProjectNotFoundException;
import com.weutil.todo.exception.TodoTaskNotFoundException;
import com.weutil.todo.mapper.TodoProjectMapper;
import com.weutil.todo.mapper.TodoTaskMapper;
import com.weutil.todo.model.TodoTaskOperation;
import com.weutil.todo.model.UpdateTodoTaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.weutil.todo.entity.table.TodoProjectTableDef.TODO_PROJECT;
import static com.weutil.todo.entity.table.TodoTaskTableDef.TODO_TASK;

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
public class TodoTaskService {
    private final TodoProjectMapper todoProjectMapper;
    private final TodoTaskMapper todoTaskMapper;
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
    public TodoTask create(long userId, long projectId, String name) {
        // 检查待办项目所属权
        checkProjectOwnership(userId, projectId);

        TodoTask inserted = TodoTask.builder().userId(userId).projectId(projectId).name(name).build();
        todoTaskMapper.insertSelective(inserted);

        TodoTask entity = getOrThrowById(userId, inserted.getId());

        // 发布待办任务创建事件
        applicationEventPublisher.publishEvent(new TodoTaskCreatedEvent(entity));

        return entity;
    }

    /**
     * 检查待办项目所属权
     *
     * <h3>开发备注
     * <p>此处实际上可以直接调用 {@link TodoProjectService} 的 {@code getOrThrowById} 方法, 为避免出现循环依赖，此处单独写一个。
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
            QueryCondition condition = TODO_PROJECT.USER_ID.eq(userId).and(TODO_PROJECT.ID.eq(projectId));
            if (todoProjectMapper.selectCountByCondition(condition) != 1) {
                throw new TodoProjectNotFoundException(projectId, userId);
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
    public TodoTask getOrThrowById(long userId, long taskId) {
        TodoTask entity = todoTaskMapper.selectOneById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new TodoTaskNotFoundException(taskId, userId);
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
    public TodoTask getWithRelationsOrThrowById(long userId, long taskId) {
        TodoTask entity = todoTaskMapper.selectOneWithRelationsById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new TodoTaskNotFoundException(taskId, userId);
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
    public List<TodoTask> listByProjectId(long userId, long projectId) {
        checkProjectOwnership(userId, projectId);

        QueryCondition condition = TODO_TASK.USER_ID.eq(userId).and(TODO_TASK.PROJECT_ID.eq(projectId));
        return todoTaskMapper.selectListByCondition(condition);
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
    public void updateWithDTO(long userId, long taskId, UpdateTodoTaskDTO dto) {
        TodoTask entity = getOrThrowById(userId, taskId);

        if (dto.getOperation() != null) {
            TodoTaskOperation operation = dto.getOperation();
            if (operation == TodoTaskOperation.COMPLETE) {
                complete(entity);
            } else if (operation == TodoTaskOperation.UNCOMPLETE) {
                uncomplete(entity);
            } else if (operation == TodoTaskOperation.CLEAR_DUE_DATETIME) {
                clearDueDateTime(entity);
            }
        } else {
            TodoTask updated = UpdateEntity.of(TodoTask.class, entity.getId());

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

            todoTaskMapper.update(updated);
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
    public void complete(TodoTask entity) {
        if (entity.getCompleteTime() == null) {
            TodoTask updated = TodoTask.builder().id(entity.getId()).completeTime(LocalDateTime.now()).build();

            todoTaskMapper.update(updated);
            applicationEventPublisher.publishEvent(new TodoTaskCompletedEvent(entity));
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
    public void uncomplete(TodoTask entity) {
        if (entity.getCompleteTime() != null) {
            TodoTask updated = UpdateEntity.of(TodoTask.class, entity.getId());
            updated.setCompleteTime(null);

            todoTaskMapper.update(updated);
            applicationEventPublisher.publishEvent(new TodoTaskUncompletedEvent(entity));
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
    public void clearDueDateTime(TodoTask entity) {
        if (entity.getDueDateTime() != null || entity.getDueDate() != null || entity.getDueTime() != null) {
            TodoTask updated = UpdateEntity.of(TodoTask.class, entity.getId());
            updated.setDueDateTime(null);
            updated.setDueDate(null);
            updated.setDueTime(null);

            todoTaskMapper.update(updated);
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
    public void move(TodoTask entity, long targetProjectId) {
        checkProjectOwnership(entity.getUserId(), targetProjectId);

        if (!entity.getProjectId().equals(targetProjectId)) {
            TodoTask updated = TodoTask.builder().id(entity.getId()).projectId(targetProjectId).build();
            todoTaskMapper.update(updated);

            applicationEventPublisher.publishEvent(new TodoTaskMovedEvent(entity, entity.getProjectId(), targetProjectId));
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
        TodoTask entity = getOrThrowById(userId, taskId);
        todoTaskMapper.deleteById(entity.getId());

        applicationEventPublisher.publishEvent(new TodoTaskDeletedEvent(entity));
    }
}
