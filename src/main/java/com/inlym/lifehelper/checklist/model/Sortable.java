package com.inlym.lifehelper.checklist.model;

/**
 * 类名称
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/3
 * @since 2.3.0
 **/
public interface Sortable {
    Long getId();

    void setId(Long id);

    Long getPrevId();

    void setPrevId(Long prevId);
}
