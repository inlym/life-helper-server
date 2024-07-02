package com.inlym.lifehelper.checklist.service;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import com.inlym.lifehelper.checklist.exception.ChecklistProjectNotFoundException;
import com.inlym.lifehelper.checklist.mapper.ChecklistProjectMapper;
import com.mybatisflex.core.query.QueryCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.inlym.lifehelper.checklist.entity.table.ChecklistProjectTableDef.CHECKLIST_PROJECT;

/**
 * 待办项目服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistProjectService {
    private final ChecklistProjectMapper checklistProjectMapper;

    /**
     * 新增
     *
     * @param entity 实体数据
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void add(ChecklistProject entity) {
        checklistProjectMapper.insertSelective(entity);
    }

    /**
     * 删除待办项目
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void delete(long userId, long projectId) {
        ChecklistProject project = findById(userId, projectId);
        if (project != null) {
            // TODO
            // 检查当前项目下的“未完成”任务数，不为0则不允许删除

            // 全部校验通过后，最后执行删除
            checklistProjectMapper.deleteById(projectId);
        }
    }

    /**
     * 通过 ID 查找待办项目
     *
     * @param userId    用户 ID
     * @param projectId 待办项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public ChecklistProject findById(long userId, long projectId) {
        ChecklistProject project = checklistProjectMapper.selectOneById(projectId);
        if (project != null) {
            // 能找到说明数据存在，继续判断是否属于指定用户
            if (Objects.equals(project.getUserId(), userId)) {
                // 归属用户 ID 校验通过
                return project;
            } else {
                // 归属用户 ID 校验未通过，当前仅打印日志，后期开发纳入安全体系预警机制
                log.trace("[疑似伪造请求攻击] userId={} 的用户尝试访问了不属于自己的 ChecklistProject(id={}) .", userId, projectId);
            }
        }
        throw new ChecklistProjectNotFoundException();
    }

    /**
     * 更新信息
     *
     * @param entity 待办项目实体对象
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void update(ChecklistProject entity) {
        checklistProjectMapper.update(entity);
    }

    /**
     * 获取指定用户的待办项目列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public List<ChecklistProject> list(long userId) {
        QueryCondition condition = CHECKLIST_PROJECT.USER_ID.eq(userId);
        return checklistProjectMapper.selectListByCondition(condition);
    }
}
