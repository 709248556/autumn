package com.autumn.zero.common.library.application.dto.tree.input;

import io.swagger.annotations.ApiModelProperty;

/**
 * 子级拼音排序查询输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 4:55
 */
public interface ChildrenPinyinSortQueryInput extends ChildrenQueryInput {

    /**
     * 获取是否拼音排序
     *
     * @return
     */
    @ApiModelProperty(value = "拼音排序")
    boolean isPinyinSort();

    /**
     * 设置是否拼音排序
     *
     * @param pinyinSort 是否拼音排序
     */
    void setPinyinSort(boolean pinyinSort);
}
