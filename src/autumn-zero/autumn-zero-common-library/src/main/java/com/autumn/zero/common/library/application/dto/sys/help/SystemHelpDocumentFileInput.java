package com.autumn.zero.common.library.application.dto.sys.help;

import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 帮助文件输入
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:56
 **/
@Getter
@Setter
public class SystemHelpDocumentFileInput extends AbstractSystemHelpDocumentInput {

    private static final long serialVersionUID = -3941492905105888899L;

    /**
     * Html内容
     */
    @ApiModelProperty(value = "Html内容(必输)")
    @NotNullOrBlank(message = "Html内容不能为空。")
    private String htmlContent;
}
