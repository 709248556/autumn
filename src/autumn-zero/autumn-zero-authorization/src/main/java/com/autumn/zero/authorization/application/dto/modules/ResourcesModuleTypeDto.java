package com.autumn.zero.authorization.application.dto.modules;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModuleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 资源类型Dto
 * 
 * @author 老码农 2019-03-06 09:13:07
 */
@ToString(callSuper = true)
public class ResourcesModuleTypeDto extends DefaultEntityDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9012565265710513440L;

	@ApiModelProperty(value = "资源类型名称")
	@NotNullOrBlank(message = "资源类型名称不能为空")
	@Length(max = ResourcesModuleType.MAX_NAME_LENGTH, message = "资源类型名称长度不能超过" + ResourcesModuleType.MAX_NAME_LENGTH
			+ "个字。")
	@ExcelColumn(order = 2, friendlyName = "资源类型名称", width = 100)
	private String name;

	/**
	 * 获取资源名称类型
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置资源名称类型
	 * 
	 * @param name
	 *            资源名称类型
	 */
	public void setName(String name) {
		this.name = name;
	}

}
