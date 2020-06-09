package com.autumn.zero.common.library.application.dto.tree.output;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.application.dto.output.CommonNameSortUserModifiedOutput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 树形输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 16:46
 */
@Getter
@Setter
public class TreeOutput extends CommonNameSortUserModifiedOutput {

    private static final long serialVersionUID = 4266241547720973711L;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id", required = false, dataType = "Long")
    private Long parentId;

    /**
     * 级别
     */
    @ApiModelProperty(value = "级别", required = false, dataType = "Integer")
    @ExcelColumn(order = 20, friendlyName = "级别", width = 80)
    private Integer level;

    /**
     * 子级数量
     */
    @ApiModelProperty(value = "子级数量", required = false, dataType = "Integer")
    @ExcelColumn(order = 21, friendlyName = "子级数量", width = 80)
    private Integer childrenCount;

    /**
     * 完整id
     */
    @ApiModelProperty(value = "完整id", required = false, dataType = "String")
    @ExcelColumn(order = 22, friendlyName = "完整id", width = 80)
    private String fullId;

    /**
     * 完整名称
     */
    @ApiModelProperty(value = "完整名称", required = false, dataType = "String")
    @ExcelColumn(order = 40, friendlyName = "完整名称", width = 300)
    private String fullName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 状态名称
     *
     * @return
     */
    @ExcelColumn(order = 50, friendlyName = "状态名称", width = 300)
    @ApiModelProperty(value = "状态名称")
    public String getStatusName() {
        return CommonStatusConstant.getName(this.getStatus());
    }
}
