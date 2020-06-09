package com.autumn.zero.common.library.application.dto.tree.input;

import com.autumn.validation.DataValidation;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * 子级查询输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 4:49
 */
public interface ChildrenQueryInput extends DataValidation {

    /**
     * 获取父级id
     *
     * @return
     */
    @ApiModelProperty(value = "父级id,未指定则查询根级")
    Long getParentId();

    /**
     * 设置父级
     *
     * @param parentId 父级id
     */
    void setParentId(Long parentId);

    /**
     * 获取状态
     * {@link com.autumn.zero.common.library.constants.CommonStatusConstant}
     */
    @ApiModelProperty(value = CommonStatusConstant.API_MODEL_PROPERTY + "，未指定则查询全部")
    Integer getStatus();

    /**
     * 设置状态
     *
     * @param status {@link com.autumn.zero.common.library.constants.CommonStatusConstant}
     */
    void setStatus(Integer status);

    /**
     * 缓存键
     *
     * @return
     */
    String toCacheKey();

}
