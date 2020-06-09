package com.autumn.zero.common.library.application.dto.sys.help;

import com.autumn.util.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 帮助文档文件输出
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:50
 **/
@Getter
@Setter
public class SystemHelpDocumentFileOutput extends SystemHelpDocumentListOutput {

    private static final long serialVersionUID = 2287597722594298503L;

    /**
     * Html内容
     */
    @ApiModelProperty(value = "Html内容")
    @ExcelColumn(order = 20, friendlyName = "Html内容", width = 80)
    private String htmlContent;
}
