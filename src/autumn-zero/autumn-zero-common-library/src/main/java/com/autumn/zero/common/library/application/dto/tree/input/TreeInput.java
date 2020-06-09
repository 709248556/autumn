package com.autumn.zero.common.library.application.dto.tree.input;

import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.common.library.application.dto.input.CommonNameSortInput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 树形输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 16:37
 */
@Getter
@Setter
public class TreeInput extends CommonNameSortInput {

    /**
     *
     */
    private static final long serialVersionUID = -6084835753239980742L;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 状态
     * {@link com.autumn.zero.common.library.constants.CommonStatusConstant}
     */
    @ApiModelProperty(value = CommonStatusConstant.API_MODEL_PROPERTY)
    private Integer status;

    @Override
    public void valid() {
        super.valid();
        if (this.getStatus() == null) {
            this.setStatus(CommonStatusConstant.STATUS_ENABLE);
        } else {
            if (!CommonStatusConstant.exist(this.getStatus())) {
                ExceptionUtils.throwValidationException("无效的状态或不支持。[" + CommonStatusConstant.API_MODEL_PROPERTY + "]");
            }
        }
    }
}
