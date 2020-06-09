package com.autumn.zero.common.library.application.dto.sys.help;

import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.application.dto.tree.input.TreeInput;
import com.autumn.zero.common.library.entities.sys.SystemHelpDocument;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 帮助文档输入
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:39
 **/
@Getter
@Setter
public abstract class AbstractSystemHelpDocumentInput extends TreeInput {

    private static final long serialVersionUID = 7448526149450679056L;

    /**
     * 友好名称
     */
    @ApiModelProperty(value = "友好名称(必输)")
    @NotNullOrBlank(message = "友好名称不能为空")
    @Length(max = SystemHelpDocument.MAX_NAME_LENGTH, message = "友好名称长度过"
            + SystemHelpDocument.MAX_NAME_LENGTH + "字。")
    private String friendlyName;

}
