package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.weutil.reminder.entity.ReminderProject;
import com.weutil.reminder.mapper.ReminderProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.weutil.reminder.entity.table.ReminderProjectTableDef.REMINDER_PROJECT;

/**
 * 待办项目服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ReminderProjectService {
    private final ReminderProjectMapper reminderProjectMapper;

    /**
     * 创建待办项目
     *
     * @param userId 用户 ID
     * @param name   项目名称
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public ReminderProject create(long userId, String name) {
        ReminderProject entity = ReminderProject.builder().userId(userId).name(name).build();
        reminderProjectMapper.insertSelective(entity);

        return entity;
    }

    /**
     * 获取指定用户的待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public List<ReminderProject> list(long userId) {
        QueryCondition condition = REMINDER_PROJECT.USER_ID.eq(userId);
        return reminderProjectMapper.selectListByCondition(condition);
    }

    /**
     * 删除待办项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public void delete(long userId, long projectId) {
        // TODO
    }
}
