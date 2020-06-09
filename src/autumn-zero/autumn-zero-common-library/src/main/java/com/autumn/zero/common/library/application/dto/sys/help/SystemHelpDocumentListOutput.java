package com.autumn.zero.common.library.application.dto.sys.help;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.entities.sys.SystemHelpDocument;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 文档帮助列表
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:46
 **/
@Getter
@Setter
public class SystemHelpDocumentListOutput extends SystemHelpDocumentOutput {

    private static final long serialVersionUID = -1502455773947883032L;



    /**
     * 是否生成
     */
    @ApiModelProperty(value = "是否生成")
    @ExcelColumn(order = 5, friendlyName = "是否生成", width = 80)
    private boolean generate;

    /**
     * 生成时间
     */
    @ApiModelProperty(value = "生成时间")
    @ExcelColumn(order = 6, friendlyName = "生成时间", width = 80)
    private Date generateTime;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    @ExcelColumn(order = 7, friendlyName = "文件大小", width = 80)
    private Long fileLength;

    /**
     * 文件友好大小
     */
    @ApiModelProperty(value = "文件友好大小(maxLength =" + SystemHelpDocument.MAX_LENGTH_FILE_FRIENDLY_LENGTH + ")")
    @ExcelColumn(order = 8, friendlyName = "文件友好大小", width = 80)
    private String fileFriendlyLength;
}
