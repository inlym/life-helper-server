package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.reminder.entity.ReminderTask;
import com.weutil.reminder.event.*;
import com.weutil.reminder.exception.ReminderProjectNotFoundException;
import com.weutil.reminder.exception.ReminderTaskNotFoundException;
import com.weutil.reminder.mapper.ReminderProjectMapper;
import com.weutil.reminder.mapper.ReminderTaskMapper;
import com.weutil.reminder.model.ReminderTaskOperation;
import com.weutil.reminder.model.UpdateReminderTaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.weutil.reminder.entity.table.ReminderProjectTableDef.REMINDER_PROJECT;

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
public class ReminderTaskService {
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
    public ReminderTask create(long userId, long projectId, String name) {
        // 检查待办项目所属权
        checkProjectOwnership(userId, projectId);

        ReminderTask inserted = ReminderTask.builder().userId(userId).projectId(projectId).name(name).build();
        reminderTaskMapper.insertSelective(inserted);

        ReminderTask entity = getOrThrowById(userId, inserted.getId());

        // 发布待办任务创建事件
        applicationEventPublisher.publishEvent(new ReminderTaskCreatedEvent(entity));

        return entity;
    }

    /**
     * 检查待办项目所属权
     *
     * <h3>开发备注
     * <p>此处实际上可以直接调用 {@link ReminderProjectService} 的 {@code getOrThrowById} 方法, 为避免出现循环依赖，此处单独写一个。
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
                throw new ReminderProjectNotFoundException(projectId, userId);
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
    public ReminderTask getOrThrowById(long userId, long taskId) {
        ReminderTask entity = reminderTaskMapper.selectOneById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new ReminderTaskNotFoundException(taskId, userId);
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
    public ReminderTask getWithRelationsOrThrowById(long userId, long taskId) {
        ReminderTask entity = reminderTaskMapper.selectOneWithRelationsById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new ReminderTaskNotFoundException(taskId, userId);
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
    public void updateWithDTO(long userId, long taskId, UpdateReminderTaskDTO dto) {
        ReminderTask entity = getOrThrowById(userId, taskId);

        if (dto.getOperation() != null) {
            ReminderTaskOperation operation = dto.getOperation();
            if (operation == ReminderTaskOperation.COMPLETE) {
                complete(entity);
            } else if (operation == ReminderTaskOperation.UNCOMPLETE) {
                uncomplete(entity);
            } else if (operation == ReminderTaskOperation.CLEAR_DUE_TIME) {
                clearDueTime(entity);
            }
        } else {
            ReminderTask updated = ReminderTask.builder().id(taskId).build();

            if (dto.getName() != null && !dto.getName().equals(entity.getName())) {
                updated.setName(dto.getName());
            }
            if (dto.getProjectId() != null && !dto.getProjectId().equals(entity.getProjectId())) {
                move(entity, dto.getProjectId());
            }
            if (dto.getContent() != null && !dto.getContent().equals(entity.getContent())) {
                updated.setContent(dto.getContent());
            }
            if (dto.getDueTime() != null && !dto.getDueTime().equals(entity.getDueTime())) {
                updated.setDueTime(dto.getDueTime());
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
    public void complete(ReminderTask entity) {
        if (entity.getCompleteTime() == null) {
            ReminderTask updated = ReminderTask.builder().id(entity.getId()).completeTime(LocalDateTime.now()).build();
            reminderTaskMapper.update(updated);

            applicationEventPublisher.publishEvent(new ReminderTaskCompletedEvent(entity));
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
    public void uncomplete(ReminderTask entity) {
        if (entity.getCompleteTime() != null) {
            ReminderTask updated = UpdateEntity.of(ReminderTask.class, entity.getId());
            updated.setCompleteTime(null);
            reminderTaskMapper.update(updated);

            applicationEventPublisher.publishEvent(new ReminderTaskUncompletedEvent(entity));
        }
    }

    /**
     * 清除截止时间
     *
     * @param entity 从数据库查询的实体
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    public void clearDueTime(ReminderTask entity) {
        if (entity.getDueTime() != null) {
            ReminderTask updated = UpdateEntity.of(ReminderTask.class, entity.getId());
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
    public void move(ReminderTask entity, long targetProjectId) {
        checkProjectOwnership(entity.getUserId(), targetProjectId);

        if (!entity.getProjectId().equals(targetProjectId)) {
            ReminderTask updated = ReminderTask.builder().id(entity.getId()).projectId(targetProjectId).build();
            reminderTaskMapper.update(updated);

            applicationEventPublisher.publishEvent(new ReminderTaskMovedEvent(entity, entity.getProjectId(), targetProjectId));
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
        ReminderTask entity = getOrThrowById(userId, taskId);
        reminderTaskMapper.deleteById(entity.getId());

        applicationEventPublisher.publishEvent(new ReminderTaskDeletedEvent(entity));
    }
}
