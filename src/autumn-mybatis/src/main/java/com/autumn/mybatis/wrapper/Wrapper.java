package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.CriteriaSection;

/**
 * @author 老码农
 * 2019-06-04 10:47:04
 */
public interface Wrapper {

    /**
     * 获取条件段
     *
     * @return
     */
    CriteriaSection getSection();
}
