package com.autumn.zero.common.library.application.dto.tree.input;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 子级查询输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 1:19
 */
@Getter
@Setter
public class DefaultChildrenQueryInput extends DefaultDataValidation implements ChildrenQueryInput {

    private static final long serialVersionUID = 4394016316591699518L;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id,未指定则查询根级")
    private Long parentId;

    /**
     * 状态
     * {@link com.autumn.zero.common.library.constants.CommonStatusConstant}
     */
    @ApiModelProperty(value = CommonStatusConstant.API_MODEL_PROPERTY + "，未指定则查询全部")
    private Integer status;


    @Override
    public void valid() {
        super.valid();
        if (this.getStatus() != null) {
            if (!CommonStatusConstant.exist(this.getStatus())) {
                ExceptionUtils.throwValidationException("无效的状态或不支持。");
            }
        }
    }

    @Override
    public String toCacheKey() {
        StringBuilder builder = new StringBuilder();
        if (this.getParentId() != null) {
            builder.append("_" + this.getParentId());
        } else {
            builder.append("_root");
        }
        if (this.getStatus() != null) {
            builder.append("_" + this.getStatus());
        } else {
            builder.append("_all");
        }
        return builder.toString();
    }
}
