package com.autumn.zero.common.library.application.dto.sys.help;

import com.autumn.application.dto.input.DefaultKeyInput;
import com.autumn.exception.ExceptionUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 帮助文档生成输入
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 11:39
 **/
@Getter
@Setter
public class SystemHelpDocumentGenerateInput extends DefaultKeyInput {

    private static final long serialVersionUID = 3130122637520973704L;

    /**
     *
     * 重置生成，即无论是否生成，均重新生成
     */
    @ApiModelProperty(value = "重置生成，即无论是否生成，均重新生成")
    private boolean resetGenerate;

    @Override
    public void valid() {
        super.valid();
        if (this.getId() == null) {
            ExceptionUtils.throwValidationException("id不能为空。");
        }
    }
}
