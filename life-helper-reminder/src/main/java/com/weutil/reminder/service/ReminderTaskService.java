package com.weutil.reminder.service;

import com.weutil.reminder.entity.ReminderTask;
import com.weutil.reminder.event.ReminderTaskCreatedEvent;
import com.weutil.reminder.exception.ReminderTaskNotFoundException;
import com.weutil.reminder.mapper.ReminderProjectMapper;
import com.weutil.reminder.mapper.ReminderTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
        ReminderTask inserted = ReminderTask.builder().userId(userId).projectId(projectId).name(name).build();
        reminderTaskMapper.insertSelective(inserted);

        ReminderTask entity = getOrThrowById(userId, inserted.getId());

        // 发布待办任务创建事件
        applicationEventPublisher.publishEvent(new ReminderTaskCreatedEvent(entity));

        return entity;
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
    private ReminderTask getOrThrowById(long userId, long taskId) {
        ReminderTask entity = reminderTaskMapper.selectOneById(taskId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new ReminderTaskNotFoundException(taskId, userId);
    }
}
