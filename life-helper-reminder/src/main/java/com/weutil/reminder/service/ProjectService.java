package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.reminder.entity.Project;
import com.weutil.reminder.exception.ProjectNotEmptyWhenDeletedException;
import com.weutil.reminder.exception.ProjectNotFoundException;
import com.weutil.reminder.mapper.ProjectMapper;
import com.weutil.reminder.model.Color;
import com.weutil.reminder.model.ProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.weutil.reminder.entity.table.ProjectTableDef.PROJECT;

/**
 * 待办项目服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectMapper projectMapper;

    /**
     * 新增项目
     *
     * @param entity 实体数据
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    public void create(Project entity) {
        projectMapper.insertSelective(entity);
    }

    /**
     * 删除项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    public void delete(long userId, long projectId) {
        Project project = findById(userId, projectId);
        if (project.getUncompletedTaskCount() > 0) {
            throw new ProjectNotEmptyWhenDeletedException();
        }

        projectMapper.deleteById(projectId);
    }

    /**
     * 通过 ID 查找项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    public Project findById(long userId, long projectId) {
        QueryCondition condition = PROJECT.USER_ID.eq(userId).and(PROJECT.ID.eq(projectId));
        Project result = projectMapper.selectOneByCondition(condition);

        if (result == null) {
            log.trace("[Reminder] project not found, userId={}, projectId={}", userId, projectId);
            throw new ProjectNotFoundException();
        }

        return result;
    }

    /**
     * 修改项目信息
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     * @param dto       请求数据
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    public void update(long userId, long projectId, ProjectDTO dto) {
        // 先查找确认存在，才有下一步操作
        Project project = findById(userId, projectId);

        Project updated = UpdateEntity.of(Project.class, projectId);
        if (dto.getName() != null) {
            updated.setName(dto.getName());
        }
        if (dto.getColorCode() != null) {
            updated.setColor(Color.fromCode(dto.getColorCode()));
        }
        if (dto.getFavorite() != null) {
            if (dto.getFavorite() && project.getFavoriteTime() == null) {
                updated.setFavoriteTime(LocalDateTime.now());
            } else if (!dto.getFavorite() && project.getFavoriteTime() != null) {
                updated.setFavoriteTime(null);
            }
        }

        projectMapper.update(updated);
    }

    /**
     * 列出所有项目
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/29
     * @since 3.0.0
     */
    public List<Project> list(long userId) {
        QueryCondition condition = PROJECT.USER_ID.eq(userId);
        return projectMapper.selectListByCondition(condition);
    }
}
