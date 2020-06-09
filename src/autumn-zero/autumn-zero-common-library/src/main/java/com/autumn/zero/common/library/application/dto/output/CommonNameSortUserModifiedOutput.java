package com.autumn.zero.common.library.application.dto.output;

import com.autumn.application.dto.output.auditing.user.DefaultUserGmtModifiedOutput;
import com.autumn.util.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共名称排序审计输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:41
 */
@ToString(callSuper = true)
@Getter
@Setter
public class CommonNameSortUserModifiedOutput extends DefaultUserGmtModifiedOutput {

    private static final long serialVersionUID = 6497910678829313026L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @ExcelColumn(order = 3, friendlyName = "名称", width = 120)
    private String name;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    @ExcelColumn(order = 4, friendlyName = "顺序", width = 60)
    private Integer sortId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelColumn(order = 500, friendlyName = "备注", width = 300)
    private String remarks;

}
