package com.weutil.todo.service;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.weutil.todo.entity.TodoProject;
import com.weutil.todo.exception.TodoProjectFailedToDeleteException;
import com.weutil.todo.exception.TodoProjectNotFoundException;
import com.weutil.todo.mapper.TodoProjectMapper;
import com.weutil.todo.mapper.TodoTaskMapper;
import com.weutil.todo.model.TodoProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.weutil.todo.entity.table.TodoProjectTableDef.TODO_PROJECT;
import static com.weutil.todo.entity.table.TodoTaskTableDef.TODO_TASK;

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
public class TodoProjectService {
    private final TodoProjectMapper todoProjectMapper;
    private final TodoTaskMapper todoTaskMapper;

    /**
     * 使用请求数据创建待办项目
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @return 新创建实体的主键 ID
     * @date 2025/01/26
     * @since 3.0.0
     */
    public long createWithDTO(long userId, TodoProjectDTO dto) {
        TodoProject inserted = TodoProject.builder()
            .userId(userId)
            .name(dto.getName())
            .emoji(dto.getEmoji())
            .color(dto.getColor())
            .build();
        if (dto.getFavorite() != null && dto.getFavorite()) {
            inserted.setFavoriteTime(LocalDateTime.now());
        }
        todoProjectMapper.insertSelective(inserted);

        return inserted.getId();
    }

    /**
     * 获取指定用户的待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/12
     * @since 3.0.0
     */
    public List<TodoProject> list(long userId) {
        QueryCondition condition = TODO_PROJECT.USER_ID.eq(userId);
        QueryWrapper queryWrapper = QueryWrapper.create().where(condition).orderBy(TODO_PROJECT.ID, false);

        return todoProjectMapper.selectListByQuery(queryWrapper);
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
        TodoProject entity = getOrThrowById(userId, projectId);

        // 下方逐个检查不允许删除的条件，并抛出异常
        // 本期内仅以下1个条件（2024.12.13）
        if (countUncompletedTasks(projectId) > 0) {
            throw new TodoProjectFailedToDeleteException("请先将当前项目下的未完成任务删除或移动至其他项目后，再操作删除");
        }

        // 同时将已完成的全部自动删除（用户侧操作前需告知）
        QueryCondition condition1 = TODO_TASK.USER_ID.eq(userId)
            .and(TODO_TASK.PROJECT_ID.eq(projectId))
            .and(TODO_TASK.COMPLETE_TIME.isNotNull());
        todoTaskMapper.deleteByCondition(condition1);

        // 完成所有检查，进行“删除”操作
        todoProjectMapper.deleteById(entity.getId());
    }

    /**
     * 根据 ID 获取实体对象，如果未找到（包含不所属于对应用户）则抛出异常
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @return 待办项目实体对象
     * @date 2024/12/13
     * @since 3.0.0
     */
    public TodoProject getOrThrowById(long userId, long projectId) {
        TodoProject entity = todoProjectMapper.selectOneById(projectId);
        if (entity != null && entity.getUserId() == userId) {
            return entity;
        }

        throw new TodoProjectNotFoundException(projectId, userId);
    }

    /**
     * 计算指定项目的未完成任务数
     *
     * @param projectId 项目 ID
     *
     * @date 2024/12/24
     * @since 3.0.0
     */
    public long countUncompletedTasks(long projectId) {
        QueryCondition condition = TODO_TASK.PROJECT_ID.eq(projectId).and(TODO_TASK.COMPLETE_TIME.isNull());
        return todoTaskMapper.selectCountByCondition(condition);
    }

    /**
     * 根据请求数据进行更新
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     * @param dto       请求数据
     *
     * @date 2024/12/26
     * @since 3.0.0
     */
    public void updateWithDTO(long userId, long projectId, TodoProjectDTO dto) {
        TodoProject entity = getOrThrowById(userId, projectId);
        TodoProject updated = UpdateEntity.of(TodoProject.class, projectId);

        if (dto.getName() != null && !dto.getName().equals(entity.getName())) {
            updated.setName(dto.getName());
        }
        if (dto.getEmoji() != null && !dto.getEmoji().equals(entity.getEmoji())) {
            updated.setEmoji(dto.getEmoji());
        }
        if (dto.getColor() != null && !dto.getColor().equals(entity.getColor())) {
            updated.setColor(dto.getColor());
        }
        if (dto.getFavorite() != null) {
            if (dto.getFavorite() && entity.getFavoriteTime() == null) {
                updated.setFavoriteTime(LocalDateTime.now());
            } else if (!dto.getFavorite() && entity.getFavoriteTime() != null) {
                updated.setFavoriteTime(null);
            }
        }

        todoProjectMapper.update(updated);
    }
}
