package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.weutil.reminder.entity.LinkTaskTag;
import com.weutil.reminder.entity.Tag;
import com.weutil.reminder.entity.Task;
import com.weutil.reminder.mapper.LinkTaskTagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.weutil.reminder.entity.table.LinkTaskTagTableDef.LINK_TASK_TAG;

/**
 * 任务标签管理服务
 *
 * <h2>说明
 * <p>专门处理“添加标签”和“移除标签”事项。
 *
 * <h2>备注
 * <p>目前的“用户 ID”字段仅记录，未用于逻辑判断。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class LinkTaskTagService {
    private final LinkTaskTagMapper linkTaskTagMapper;
    private final TaskService taskService;
    private final TagService tagService;

    /**
     * 给任务添加一个标签
     *
     * @param userId 用户 ID
     * @param taskId 任务 ID
     * @param tagId  标签 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public LinkTaskTag append(long userId, long taskId, long tagId) {
        // 先检查是否存在，若已存在则不进行任何操作
        LinkTaskTag result = findOne(taskId, tagId);
        if (result != null) {
            return result;
        }

        // 创建前检查“任务”和“标签”的所属权是否归属当前用户
        verifyOwnership(userId, taskId, tagId);

        // 创建关联关系
        LinkTaskTag inserted = LinkTaskTag.builder().userId(userId).taskId(taskId).tagId(tagId).build();
        linkTaskTagMapper.insertSelective(inserted);

        return linkTaskTagMapper.selectOneById(inserted.getId());
    }

    /**
     * 通过 ID 查找
     *
     * <h3>说明
     * <p>未找到返回了 {@code null}, 而不是报错。
     *
     * @param taskId 任务 ID
     * @param tagId  标签 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public LinkTaskTag findOne(long taskId, long tagId) {
        QueryCondition condition = LINK_TASK_TAG.TASK_ID.eq(taskId).and(LINK_TASK_TAG.TAG_ID.eq(tagId));
        return linkTaskTagMapper.selectOneByCondition(condition);
    }

    /**
     * 校验“任务”和“标签”的所属权
     *
     * @param userId 用户 ID
     * @param taskId 任务 ID
     * @param tagId  标签 ID
     *
     * @date 2024/7/31
     * @since 3.0.0
     */
    private void verifyOwnership(long userId, long taskId, long tagId) {
        Task task = taskService.findById(userId, taskId);
        Tag tag = tagService.findById(userId, tagId);

        log.trace("[Reminder] task and tag verification passed, taskId={}, tagId={}", task.getId(), tag.getId());
    }

    /**
     * 移除任务的标签
     *
     * @param taskId 任务 ID
     * @param tagId  标签 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public void remove(long taskId, long tagId) {
        LinkTaskTag result = findOne(taskId, tagId);
        if (result != null) {
            linkTaskTagMapper.deleteById(result.getId());
        }
    }

    /**
     * 设置任务的标签
     *
     * <h3>备注
     * <p>直接定义新的标签列表，而不是简单的“添加”或“移除”。
     *
     * @param userId       用户 ID
     * @param taskId       任务 ID
     * @param newTagIdList 新的标签 ID 列表
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public void setTags(long userId, long taskId, List<Long> newTagIdList) {
        List<Long> currentTagIdList = listTagIds(taskId);

        for (Long tagId : newTagIdList) {
            // 新的列表有的，而当前没有的，则是需要“新增”的
            if (!currentTagIdList.contains(tagId)) {
                // 目前先这么写，后期再优化
                LinkTaskTag inserted = LinkTaskTag.builder().userId(userId).taskId(taskId).tagId(tagId).build();
                linkTaskTagMapper.insertSelective(inserted);
            }
        }
        for (Long tagId : currentTagIdList) {
            // 当前有的，而新的列表没有的，则是需要“移除”的
            if (!newTagIdList.contains(tagId)) {
                // 目前先这么写，后期再优化
                QueryCondition condition1 = LINK_TASK_TAG.TASK_ID.eq(taskId).and(LINK_TASK_TAG.TAG_ID.eq(tagId));
                linkTaskTagMapper.deleteByCondition(condition1);
            }
        }
    }

    /**
     * 获取指定任务的标签 ID 列表
     *
     * @param taskId 任务 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public List<Long> listTagIds(long taskId) {
        return list(taskId).stream().map(LinkTaskTag::getTagId).toList();
    }

    /**
     * 获取指定任务的标签列表
     *
     * @param taskId 任务 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    public List<LinkTaskTag> list(long taskId) {
        QueryCondition condition = LINK_TASK_TAG.TASK_ID.eq(taskId);
        return linkTaskTagMapper.selectListByCondition(condition);
    }
}
