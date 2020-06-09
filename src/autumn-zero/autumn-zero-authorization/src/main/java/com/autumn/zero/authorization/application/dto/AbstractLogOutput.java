package com.autumn.zero.authorization.application.dto;

import com.autumn.application.dto.output.auditing.gmt.GmtCreateOutput;
import com.autumn.util.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日志输出抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 1:52
 */
@Getter
@Setter
@ToString(callSuper = true)
public abstract class AbstractLogOutput extends GmtCreateOutput<Long> {

    private static final long serialVersionUID = -3877436850240292869L;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", required = false, dataType = "String")
    @ExcelColumn(order = 1, friendlyName = "用户名称", width = 120)
    private String userName;

    /**
     * 客户端ip
     */
    @ApiModelProperty(value = "客户端ip", required = false, dataType = "String")
    @ExcelColumn(order = 100, friendlyName = "客户端ip", width = 100)
    private String clientIpAddress;

    /**
     * 客户端名称
     */
    @ApiModelProperty(value = "客户端名称", required = false, dataType = "String")
    @ExcelColumn(order = 101, friendlyName = "客户端名称", width = 100)
    private String clientName;

    /**
     * 客户端版本
     */
    @ApiModelProperty(value = "客户端版本", required = false, dataType = "String")
    @ExcelColumn(order = 102, friendlyName = "客户端版本", width = 100)
    private String clientVersion;

    /**
     * 浏览器名称
     */
    @ApiModelProperty(value = "浏览器名称", required = false, dataType = "String")
    @ExcelColumn(order = 103, friendlyName = "浏览器名称", width = 100)
    private String browserName;

    /**
     * 浏览器平台
     */
    @ApiModelProperty(value = "浏览器平台", required = false, dataType = "String")
    @ExcelColumn(order = 104, friendlyName = "浏览器平台", width = 100)
    private String browserPlatform;

    /**
     * 浏览器信息
     */
    @ApiModelProperty(value = "浏览器信息", required = false, dataType = "String")
    @ExcelColumn(order = 105, friendlyName = "浏览器信息", width = 300)
    private String browserInfo;

}
