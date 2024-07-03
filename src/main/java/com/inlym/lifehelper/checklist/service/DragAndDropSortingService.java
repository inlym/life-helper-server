package com.inlym.lifehelper.checklist.service;

import com.inlym.lifehelper.checklist.mapper.ChecklistProjectMapper;
import com.inlym.lifehelper.checklist.model.Sortable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 拖拽排序服务
 *
 * <h2>说明
 * <p>对于项目、标签、任务等3项在前端页面中均支持进行拖拽排序，通过控制 {@code prevId} 属性来进行排序。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/2
 * @since 2.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class DragAndDropSortingService {
    private final ChecklistProjectMapper checklistProjectMapper;

    /**
     * 移动到指定项目前
     *
     * @param moved  被移动的项目 ID
     * @param target 目标项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void movedBeforeTarget(Sortable moved, Sortable target) {}

    /**
     * 移动到尾部
     *
     * @param moved 被移动的项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void movedToEnd(Sortable moved) {}

    /**
     * 新创建项目后，将原来的顶部项目排到第2名
     *
     * @param newlyCreated 新创建的项目 ID
     *
     * @date 2024/7/2
     * @since 2.3.0
     */
    public void moveOriginalTopToSecond(Sortable newlyCreated) {}

    /**
     * 对列表进行重排序
     *
     * @param list 原始列表
     *
     * @return 排序后的列表
     * @date 2024/7/2
     * @since 2.3.0
     */
    public List<Sortable> sort(List<Sortable> list) {
        return null;
    }
}
