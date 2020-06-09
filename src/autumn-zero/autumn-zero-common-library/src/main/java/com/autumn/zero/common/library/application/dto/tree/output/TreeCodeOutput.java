package com.autumn.zero.common.library.application.dto.tree.output;

import com.autumn.util.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 具有代码的树形输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 16:50
 */
@Getter
@Setter
public class TreeCodeOutput extends TreeOutput {
    private static final long serialVersionUID = 5995782320677507895L;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码", required = true, dataType = "String")
    @ExcelColumn(order = 10, friendlyName = "代码", width = 80)
    private String code;

    /**
     * 完整代码
     */
    @ApiModelProperty(value = "完整代码", required = false, dataType = "String")
    @ExcelColumn(order = 31, friendlyName = "完整代码", width = 300)
    private String fullCode;

}
