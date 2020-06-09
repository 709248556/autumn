package com.autumn.zero.authorization.application.dto;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录日志输出
 *
 * @author 老码农 2018-12-11 15:42:45
 */
@Getter
@Setter
@ToString(callSuper = true)
@ExcelWorkSheet(exportTitle = "用户登录日志")
public class UserLoginLogOutput extends AbstractLogOutput {

    /**
     *
     */
    private static final long serialVersionUID = 3844454373121194957L;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号", required = false, dataType = "String")
    @ExcelColumn(order = 2, friendlyName = "登录账号", width = 120)
    private String userAccount;

    /**
     * 第三方提供者
     */
    @ApiModelProperty(value = "第三方提供者", required = false, dataType = "String")
    @ExcelColumn(order = 3, friendlyName = "第三方提供者", width = 120)
    private String provider;

    /**
     * 第三方提供键
     */
    @ApiModelProperty(value = "第三方提供键", required = false, dataType = "String")
    @ExcelColumn(order = 4, friendlyName = "第三方提供键", width = 150)
    private String providerKey;

    /**
     * 成功
     */
    @ApiModelProperty(value = "成功", required = false, dataType = "Boolean")
    @ExcelColumn(order = 5, friendlyName = "成功", width = 80)
    private boolean success;

    /**
     * 状态消息
     */
    @ApiModelProperty(value = "状态消息", required = false, dataType = "String")
    @ExcelColumn(order = 6, friendlyName = "状态消息", width = 120)
    private String statusMessage;

}
