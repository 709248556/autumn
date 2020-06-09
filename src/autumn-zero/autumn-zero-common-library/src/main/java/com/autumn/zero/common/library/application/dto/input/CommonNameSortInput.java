package com.autumn.zero.common.library.application.dto.input;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.validation.DataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.AbstractNameUserAuditingStatusEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 公共名称顺序输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:09
 */
@ToString(callSuper = true)
@Getter
@Setter
public class CommonNameSortInput extends DefaultEntityDto implements DataValidation {

    private static final long serialVersionUID = -8331333891729421104L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotNullOrBlank(message = "名称不能为空")
    @Length(max = AbstractNameUserAuditingStatusEntity.MAX_NAME_LENGTH, message = "名称长度过"
            + AbstractNameUserAuditingStatusEntity.MAX_NAME_LENGTH + "字。")
    private String name;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    @NotNull(message = "顺序不能为空。")
    private Integer sortId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Override
    public void valid() {
        super.valid();
        if (this.getSortId() == null) {
            this.setSortId(1);
        }
    }
}
