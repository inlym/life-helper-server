package com.weutil.reminder.service;

import com.mybatisflex.core.query.QueryCondition;
import com.weutil.reminder.entity.Tag;
import com.weutil.reminder.exception.TagNotFoundException;
import com.weutil.reminder.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.weutil.reminder.entity.table.TagTableDef.TAG;

/**
 * 标签管理服务
 *
 * <h2>说明
 * <p>仅用于管理“标签”本身，不负责管理与“任务”的关联关系。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/31
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagMapper tagMapper;

    /**
     * 通过 ID 查找标签
     *
     * @param userId 用户 ID
     * @param tagId  标签 ID
     *
     * @date 2024/7/31
     * @since 3.0.0
     */
    public Tag findById(long userId, long tagId) {
        QueryCondition condition = TAG.ID.eq(tagId).and(TAG.USER_ID.eq(userId));
        Tag result = tagMapper.selectOneByCondition(condition);

        if (result == null) {
            log.trace("[Reminder] tag not found, userId={}, tagId={}", userId, tagId);
            throw new TagNotFoundException();
        }

        return result;
    }
}
