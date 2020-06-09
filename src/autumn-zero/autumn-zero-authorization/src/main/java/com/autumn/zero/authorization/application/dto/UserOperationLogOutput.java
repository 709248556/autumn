package com.autumn.zero.authorization.application.dto;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.zero.authorization.entities.common.log.UserOperationLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户操作日志
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 2:19
 */
@Getter
@Setter
@ToString(callSuper = true)
@ExcelWorkSheet(exportTitle = "用户操作日志")
public class UserOperationLogOutput extends AbstractLogOutput {

    private static final long serialVersionUID = 3299333255388336658L;

    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称(maxLength =" + UserOperationLog.MAX_LENGTH_MODULE_NAME + ")", required = false, dataType = "String")
    @ExcelColumn(order = 1, friendlyName = "模块名称", width = 120)
    private String moduleName;

    /**
     * 操作名称
     */
    @ApiModelProperty(value = "操作名称(maxLength =" + UserOperationLog.MAX_LENGTH_OPERATION_NAME + ")", required = false, dataType = "String")
    @ExcelColumn(order = 2, friendlyName = "操作名称", width = 120)
    private String operationName;

    /**
     * 操作详情
     */
    @ApiModelProperty(value = "操作详情", required = false, dataType = "String")
    @ExcelColumn(order = 3, friendlyName = "操作详情", width = 300)
    private String logDetails;
}
