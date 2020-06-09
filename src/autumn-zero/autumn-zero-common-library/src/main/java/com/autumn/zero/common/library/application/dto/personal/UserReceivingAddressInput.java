package com.autumn.zero.common.library.application.dto.personal;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.validation.annotation.MobilePhone;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.personal.UserPersonalReceivingAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户收货地址输入
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:41
 **/
@Getter
@Setter
public class UserReceivingAddressInput extends DefaultEntityDto {

    private static final long serialVersionUID = 5911591601278458835L;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人(必输)(maxLength =" + UserPersonalReceivingAddress.MAX_LENGTH_CONTACTS_NAME + ")")
    @NotNullOrBlank(message = "联系人不能为空。")
    @Length(max = UserPersonalReceivingAddress.MAX_LENGTH_CONTACTS_NAME, message = "联系人 不能超过 " + UserPersonalReceivingAddress.MAX_LENGTH_CONTACTS_NAME + " 个字。")
    private String contactsName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号(必输)(maxLength =" + UserPersonalReceivingAddress.MAX_LENGTH_MOBILE_PHONE + ")")
    @NotNullOrBlank(message = "手机号不能为空。")
    @MobilePhone(message = "手机号 不是有效的手机号码。")
    @Length(max = UserPersonalReceivingAddress.MAX_LENGTH_MOBILE_PHONE, message = "手机号 不能超过 " + UserPersonalReceivingAddress.MAX_LENGTH_MOBILE_PHONE + " 个字。")
    private String mobilePhone;

    /**
     * 行政区id
     */
    @ApiModelProperty(value = "行政区id(必输)")
    @NotNull(message = "行政区id不能为空。")
    private Long regionId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址(必输)(maxLength =" + UserPersonalReceivingAddress.MAX_LENGTH_DETAILS_ADDRESS + ")")
    @NotNullOrBlank(message = "详细地址不能为空。")
    @Length(max = UserPersonalReceivingAddress.MAX_LENGTH_DETAILS_ADDRESS, message = "详细地址 不能超过 " + UserPersonalReceivingAddress.MAX_LENGTH_DETAILS_ADDRESS + " 个字。")
    private String detailsAddress;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private boolean defaulted;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签(maxLength =" + UserPersonalReceivingAddress.MAX_LENGTH_LABEL + ")")
    @Length(max = UserPersonalReceivingAddress.MAX_LENGTH_LABEL, message = "标签 不能超过 " + UserPersonalReceivingAddress.MAX_LENGTH_LABEL + " 个字。")
    private String label;

}