package com.autumn.zero.common.library.application.dto.input;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.application.dto.input.StatusInput;
import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 公共状态输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 16:04
 */
@Setter
@Getter
public class CommonStatusInput extends DefaultDataValidation implements StatusInput<Long> {

    private static final long serialVersionUID = -7292431390123916987L;

    @ApiModelProperty(value = "主键id")
    @NotNull(message = "主键id不能为空")
    private Long id;

    /**
     * 状态
     */
    @ApiModelProperty(value = CommonStatusConstant.API_MODEL_PROPERTY)
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Override
    public void valid() {
        super.valid();
        if (!CommonStatusConstant.exist(this.getStatus())) {
            ExceptionUtils.throwValidationException("无效的状态或不支持。");
        }
    }
}
