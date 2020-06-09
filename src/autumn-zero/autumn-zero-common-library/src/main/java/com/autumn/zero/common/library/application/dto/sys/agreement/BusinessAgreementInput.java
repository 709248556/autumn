package com.autumn.zero.common.library.application.dto.sys.agreement;

import com.autumn.exception.ExceptionUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.application.dto.input.CommonNameSortInput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 业务协议输入
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:11
 **/
@Getter
@Setter
public class BusinessAgreementInput extends CommonNameSortInput {
    private static final long serialVersionUID = 5184277879900653494L;

    /**
     * 协议类型
     */
    @ApiModelProperty(value = "协议类型(必输)")
    @NotNull(message = "协议类型不能为空。")
    private Integer agreementType;

    /**
     * 默认协议
     */
    @ApiModelProperty(value = "默认协议")
    private boolean defaultAgreement;

    /**
     * 状态
     * {@link com.autumn.zero.common.library.constants.CommonStatusConstant}
     */
    @ApiModelProperty(value = CommonStatusConstant.API_MODEL_PROPERTY)
    private Integer status;

    /**
     * 协议内容Html
     */
    @ApiModelProperty(value = "协议内容Html(必输)")
    @NotNullOrBlank(message = "协议内容不能为空。")
    private String agreementContent;

    @Override
    public void valid() {
        super.valid();
        if (this.getStatus() == null) {
            this.setStatus(CommonStatusConstant.STATUS_ENABLE);
        } else {
            if (!CommonStatusConstant.exist(this.getStatus())) {
                ExceptionUtils.throwValidationException("无效的状态或不支持。[" + CommonStatusConstant.API_MODEL_PROPERTY + "]");
            }
        }
    }
}
