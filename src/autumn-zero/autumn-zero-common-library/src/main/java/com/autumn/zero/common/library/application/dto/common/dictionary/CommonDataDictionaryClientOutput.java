package com.autumn.zero.common.library.application.dto.common.dictionary;

import com.autumn.application.dto.EntityDto;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共客户端数据字典输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 16:17
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class CommonDataDictionaryClientOutput extends EntityDto<Long> {

    private static final long serialVersionUID = -7349388891454889017L;

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

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelColumn(order = 500, friendlyName = "备注", width = 300)
    private String remarks;
}
