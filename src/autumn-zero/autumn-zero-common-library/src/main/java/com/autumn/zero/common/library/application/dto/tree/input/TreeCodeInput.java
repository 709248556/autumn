package com.autumn.zero.common.library.application.dto.tree.input;

import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.AbstractTreeCodeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 具有代码的树形输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 16:44
 */
@Getter
@Setter
public class TreeCodeInput extends TreeInput {

    private static final long serialVersionUID = -221345900716988064L;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码(maxLength =" + AbstractTreeCodeEntity.MAX_LENGTH_CODE + ")", required = false, dataType = "String")
    @Length(max = AbstractTreeCodeEntity.MAX_LENGTH_CODE, message = "代码 不能超过 " + AbstractTreeCodeEntity.MAX_LENGTH_CODE + " 个字。")
    @NotNullOrBlank(message = "代码不能为空。")
    private String code;
}
