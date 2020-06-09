package com.autumn.zero.common.library.application.dto.sys.agreement;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.application.dto.output.CommonNameSortUserModifiedOutput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户协议列表输出
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:13
 **/
@Getter
@Setter
public class BusinessAgreementListOutput extends CommonNameSortUserModifiedOutput {

    private static final long serialVersionUID = 3462771923935350261L;

    /**
     * 协议类型
     */
    @ApiModelProperty(value = "协议类型(必输)")
    private Integer agreementType;

    /**
     * 协议类型名称
     */
    @ExcelColumn(order = 1, friendlyName = "协议类型名称", width = 80)
    @ApiModelProperty(value = "协议类型名称")
    private String agreementTypeName;

    /**
     * 默认协议
     */
    @ApiModelProperty(value = "默认协议")
    @ExcelColumn(order = 2, friendlyName = "默认协议", width = 80)
    private boolean defaultAgreement;

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
