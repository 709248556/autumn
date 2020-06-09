package com.autumn.zero.common.library.application.dto.tree.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 子级拼音排序查询输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 4:57
 */
@Getter
@Setter
public class DefaultChildrenPinyinSortQueryInput extends DefaultChildrenQueryInput implements ChildrenPinyinSortQueryInput {

    private static final long serialVersionUID = 2512501566601924564L;

    /**
     * 拼音排序
     */
    @ApiModelProperty(value = "拼音排序")
    private boolean pinyinSort;

    @Override
    public String toCacheKey() {
        return super.toCacheKey() + "_pinyin_sort" + this.isPinyinSort();
    }
}
