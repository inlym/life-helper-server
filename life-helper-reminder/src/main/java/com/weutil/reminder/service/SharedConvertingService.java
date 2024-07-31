package com.weutil.reminder.service;

import com.weutil.reminder.entity.LinkTaskTag;
import com.weutil.reminder.entity.Project;
import com.weutil.reminder.model.LinkTaskTagVO;
import com.weutil.reminder.model.ProjectVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 共用数据转换服务
 *
 * <h2>主要用途
 * <p>将实体对象转换为视图对象，主要用于控制器层的数据转换。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/31
 * @since 3.0.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SharedConvertingService {
    /**
     * 将“项目”的实体对象转化为视图对象
     *
     * @param entity 项目实体对象
     *
     * @date 2024/7/28
     * @since 3.0.0
     */
    public ProjectVO convertProject(Project entity) {
        return ProjectVO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .color(entity.getColor())
                .uncompletedTaskCount(entity.getUncompletedTaskCount())
                .build();
    }

    /**
     * 将实体对象转化为视图对象
     *
     * @param entity 实体对象
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    private LinkTaskTagVO convertLinkTaskTag(LinkTaskTag entity) {
        return LinkTaskTagVO.builder().id(entity.getId()).createTime(entity.getCreateTime()).tagId(entity.getTagId()).build();
    }
}
