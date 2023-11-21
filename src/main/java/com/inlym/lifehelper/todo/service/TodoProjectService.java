package com.inlym.lifehelper.todo.service;

import com.inlym.lifehelper.todo.entity.TodoProject;
import com.inlym.lifehelper.todo.exception.TodoProjectNotFoundException;
import com.inlym.lifehelper.todo.mapper.TodoProjectMapper;
import com.mybatisflex.core.query.QueryCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.inlym.lifehelper.todo.entity.table.TodoProjectTableDef.TODO_PROJECT;

/**
 * 待办事项清单服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/10/10
 * @since 2.0.3
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class TodoProjectService {
    private final TodoProjectMapper todoProjectMapper;

    /**
     * 新增项目
     *
     * @param userId 用户 ID
     * @param name   项目名称
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    public TodoProject add(int userId, String name) {
        TodoProject project = TodoProject
            .builder()
            .userId(userId)
            .name(name)
            .build();

        todoProjectMapper.insertSelective(project);
        return project;
    }

    /**
     * 删除项目
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     *
     * @return 删除成功返回 `true`，删除失败（不存在或已删除）返回 `false`
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    public boolean delete(int userId, long projectId) {
        TodoProject project = todoProjectMapper.selectOneById(projectId);
        if (project != null && project.getUserId() == userId) {
            todoProjectMapper.deleteById(projectId);
            return true;
        }
        return false;
    }

    /**
     * 获取项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    public List<TodoProject> list(int userId) {
        return todoProjectMapper.selectListByCondition(QueryCondition.create(TODO_PROJECT.USER_ID, userId));
    }

    /**
     * 编辑项目名称
     *
     * @param userId    用户 ID
     * @param projectId 项目 ID
     * @param name      修改后的项目名称
     *
     * @date 2023/11/22
     * @since 2.0.3
     */
    public TodoProject editName(int userId, long projectId, String name) {
        TodoProject project = todoProjectMapper.selectOneById(projectId);
        if (project != null && project.getUserId() == userId) {
            TodoProject updated = TodoProject
                .builder()
                .id(projectId)
                .userId(userId)
                .name(name)
                .build();
            todoProjectMapper.update(updated);
            return updated;
        }

        throw new TodoProjectNotFoundException();
    }
}
