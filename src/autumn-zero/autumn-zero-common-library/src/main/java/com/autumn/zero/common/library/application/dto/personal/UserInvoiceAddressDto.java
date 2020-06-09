package com.autumn.zero.common.library.application.dto.personal;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.personal.UserPersonalInvoiceAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 用户发票地址Dto
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 17:02
 **/
@Getter
@Setter
public class UserInvoiceAddressDto extends DefaultEntityDto {

    private static final long serialVersionUID = 7059989872531583256L;

    /**
     * 开票名称(抬头)
     */
    @ApiModelProperty(value = "开票名称(抬头)(必输)(maxLength =" + UserPersonalInvoiceAddress.MAX_LENGTH_NAME + ")")
    @NotNullOrBlank(message = "开票名称(抬头)不能为空。")
    @Length(max = UserPersonalInvoiceAddress.MAX_LENGTH_NAME, message = "开票名称(抬头) 不能超过 " + UserPersonalInvoiceAddress.MAX_LENGTH_NAME + " 个字。")
    private String name;

    /**
     * 发票税号
     */
    @ApiModelProperty(value = "发票税号(必输)(maxLength =" + UserPersonalInvoiceAddress.MAX_LENGTH_TAX_NUMBER + ")")
    @NotNullOrBlank(message = "发票税号不能为空。")
    @Length(max = UserPersonalInvoiceAddress.MAX_LENGTH_TAX_NUMBER, message = "发票税号 不能超过 " + UserPersonalInvoiceAddress.MAX_LENGTH_TAX_NUMBER + " 个字。")
    private String taxNumber;

    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行(maxLength =" + UserPersonalInvoiceAddress.MAX_LENGTH_BANK_NAME + ")")
    @Length(max = UserPersonalInvoiceAddress.MAX_LENGTH_BANK_NAME, message = "开户银行 不能超过 " + UserPersonalInvoiceAddress.MAX_LENGTH_BANK_NAME + " 个字。")
    private String bankName;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号(maxLength =" + UserPersonalInvoiceAddress.MAX_LENGTH_BANK_ACCOUNT + ")")
    @Length(max = UserPersonalInvoiceAddress.MAX_LENGTH_BANK_ACCOUNT, message = "银行账号 不能超过 " + UserPersonalInvoiceAddress.MAX_LENGTH_BANK_ACCOUNT + " 个字。")
    private String bankAccount;

    /**
     * 企业/单位地址
     */
    @ApiModelProperty(value = "企业/单位地址(maxLength =" + UserPersonalInvoiceAddress.MAX_LENGTH_ADDRESS + ")")
    @Length(max = UserPersonalInvoiceAddress.MAX_LENGTH_ADDRESS, message = "企业/单位地址 不能超过 " + UserPersonalInvoiceAddress.MAX_LENGTH_ADDRESS + " 个字。")
    private String address;

    /**
     * 企业/单位电话
     */
    @ApiModelProperty(value = "企业/单位电话(maxLength =" + UserPersonalInvoiceAddress.MAX_LENGTH_TELEPHONE + ")")
    @Length(max = UserPersonalInvoiceAddress.MAX_LENGTH_TELEPHONE, message = "企业/单位电话 不能超过 " + UserPersonalInvoiceAddress.MAX_LENGTH_TELEPHONE + " 个字。")
    private String telephone;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private boolean defaulted;

}
