package com.autumn.zero.authorization.application.dto.users;

import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.Date;

/**
 * 用户输出
 * 
 * @author 老码农 2018-12-10 15:34:47
 */
@ToString(callSuper = true)
@ExcelWorkSheet(exportTitle = "用户信息")
public class UserOutput extends UserDto implements GmtCreateAuditing, GmtModifiedAuditing {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4323569724429190812L;

	@ApiModelProperty(value = "是否激活电话")
	@ExcelColumn(order = 7, friendlyName = "激活电话", width = 60)
	private boolean isActivatePhone;

	@ApiModelProperty(value = "是否激活邮件")
	@ExcelColumn(order = 8, friendlyName = "激活邮件", width = 60)
	private boolean isActivateEmail;

	/**
	 * 状态{@link com.autumn.security.constants.UserStatusConstants}
	 */
	@ApiModelProperty(value = "状态")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@ExcelColumn(order = 100, friendlyName = "创建时间", width = 145)
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@ExcelColumn(order = 101, friendlyName = "修改时间", width = 145)
	private Date gmtModified;

	/**
	 * 获取是否激活电话
	 * 
	 * @return
	 */
	public boolean getIsActivatePhone() {
		return isActivatePhone;
	}

	/**
	 * 设置是否激活电话
	 * 
	 * @param isActivatePhone
	 *            是否激活电话
	 */
	public void setIsActivatePhone(boolean isActivatePhone) {
		this.isActivatePhone = isActivatePhone;
	}

	/**
	 * 是否激活邮件
	 * 
	 * @return
	 */
	public boolean getIsActivateEmail() {
		return isActivateEmail;
	}

	/**
	 * 设置激活邮件
	 * 
	 * @param isActivateEmail
	 *            激活邮件
	 */
	public void setIsActivateEmail(boolean isActivateEmail) {
		this.isActivateEmail = isActivateEmail;
	}

	/**
	 * 获取用户状态
	 * 
	 * @return {@link com.autumn.security.constants.UserStatusConstants}
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置用户状态
	 * 
	 * @param status
	 *            {@link com.autumn.security.constants.UserStatusConstants}
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取状态名称
	 * 
	 * @return
	 */
	@ExcelColumn(order = 10, friendlyName = "状态", width = 60)
	public String getStatusName() {
		return UserStatusConstants.getName(this.getStatus());
	}

	@Override
	public Date getGmtCreate() {
		return gmtCreate;
	}

	@Override
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public Date getGmtModified() {
		return gmtModified;
	}

	@Override
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
