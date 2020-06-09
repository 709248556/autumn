package com.autumn.zero.common.library.application.dto.common.dictionary;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.application.dto.output.CommonNameSortUserModifiedOutput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共字典输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:39
 */
@ToString(callSuper = true)
@Getter
@Setter
public class CommonDataDictionaryOutput extends CommonNameSortUserModifiedOutput {

    private static final long serialVersionUID = 2021350384615097355L;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型类型")
    private Integer dictionaryType;

    /**
     * 系统字典
     */
    @ApiModelProperty(value = "系统字典")
    private boolean sysDictionary;

    /**
     * 系统字典值
     */
    @ApiModelProperty(value = "系统字典值")
    private Integer sysDictionaryValue;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识")
    private Integer identification;

    @ApiModelProperty(value = "状态(1=启用或2=停用)")
    private Integer status;

    /**
     * 获取状态名称
     *
     * @return
     */
    @ExcelColumn(order = 3, friendlyName = "状态", width = 80)
    public String getStatusName() {
        return CommonStatusConstant.getName(this.getStatus());
    }

}
