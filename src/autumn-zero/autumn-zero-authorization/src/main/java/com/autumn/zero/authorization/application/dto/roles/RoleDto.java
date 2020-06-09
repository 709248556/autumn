package com.autumn.zero.authorization.application.dto.roles;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.security.constants.RoleStatusConstants;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 角色Dto
 *
 * @author 老码农 2018-12-10 13:01:31
 */
@ToString(callSuper = true)
@ExcelWorkSheet(exportTitle = "角色信息")
public class RoleDto extends DefaultEntityDto {

    /**
     *
     */
    private static final long serialVersionUID = 8486328214684761698L;

    /**
     *
     */
    @ApiModelProperty(value = "顺序")
    @NotNull(message = "顺序不能为空")
    @ExcelColumn(order = 1, friendlyName = "顺序", width = 80)
    private Integer sortId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @NotNullOrBlank(message = "角色名称不能为空")
    @Length(max = AbstractRole.MAX_NAME_LENGTH, message = "角色名称长度不能超过" + AbstractRole.MAX_NAME_LENGTH + "个字。")
    @ExcelColumn(order = 2, friendlyName = "角色名称", width = 100)
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty(value = RoleStatusConstants.API_MODEL_PROPERTY)
    private Integer status;

    /**
     * 是否默认角色
     */
    @ApiModelProperty(value = "是否默认角色")
    @ExcelColumn(order = 4, friendlyName = "默认角色", width = 60)
    private boolean isDefault;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "说明")
    @Length(max = AbstractRole.MAX_SUMMARY_LENGTH, message = "角色摘要长度不能超过" + AbstractRole.MAX_SUMMARY_LENGTH + "个字。")
    @ExcelColumn(order = 1000, friendlyName = "说明", width = 300)
    private String summary;

    /**
     * 实例化 AbstractRole
     */
    public RoleDto() {

    }

    /**
     * 获取顺序
     *
     * @return
     */
    public Integer getSortId() {
        return sortId;
    }

    /**
     * 设置顺序
     *
     * @param sortId
     */
    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    /**
     * 获取角色名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * 获取状态
	 *
	 * @return {@link com.autumn.security.constants.RoleStatusConstants}
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 *
	 * @param status 状态 {@link com.autumn.security.constants.RoleStatusConstants}
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

    /**
     * 获取是否默认
     *
     * @return
     */
    public boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认
     *
     * @param isDefault 是否默认
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取摘要
     *
     * @return
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     *
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
