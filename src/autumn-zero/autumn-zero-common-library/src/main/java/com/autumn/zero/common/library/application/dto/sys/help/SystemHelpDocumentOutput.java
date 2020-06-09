package com.autumn.zero.common.library.application.dto.sys.help;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.application.dto.tree.output.TreeOutput;
import com.autumn.zero.common.library.entities.sys.SystemHelpDocument;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 帮助文档输出
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:42
 **/
@Getter
@Setter
public class SystemHelpDocumentOutput extends TreeOutput {

    private static final long serialVersionUID = -1569148259151957669L;

    /**
     * 友好名称
     */
    @ApiModelProperty(value = "友好名称")
    @ExcelColumn(order = 1, friendlyName = "是否为目录", width = 80)
    private String friendlyName;

    /**
     * 是否为目录
     */
    @ApiModelProperty(value = "是否为目录")
    @ExcelColumn(order = 2, friendlyName = "是否为目录", width = 80)
    private boolean directory;

    /**
     * url路径
     */
    @ApiModelProperty(value = "url路径(maxLength =" + SystemHelpDocument.MAX_LENGTH_URL_FULL_PATH + ")")
    @ExcelColumn(order = 3, friendlyName = "url路径", width = 80)
    private String urlFullPath;

    /**
     * 访问路径
     */
    @ApiModelProperty(value = "访问路径(maxLength =" + SystemHelpDocument.MAX_LENGTH_ACCESS_PATH + ")")
    @ExcelColumn(order = 4, friendlyName = "访问路径", width = 80)
    private String accessPath;

}
