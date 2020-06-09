package com.autumn.zero.authorization.application.dto.users;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.validation.annotation.EmailAddress;
import com.autumn.validation.annotation.MobilePhone;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户 Dto
 * 
 * @author 老码农 2018-12-10 13:04:05
 */
@ToString(callSuper = true)
public class UserDto extends DefaultEntityDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3342622931937338243L;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	@NotNullOrBlank(message = "用户账号不能为空")
	@Length(max = AbstractUser.MAX_USER_NAME_LENGTH, message = "用户账号长度不能超过" + AbstractUser.MAX_USER_NAME_LENGTH + "个字。")
	@ExcelColumn(order = 1, friendlyName = "用户账号", width = 100)
	private String userName;

	/**
	 * 真实名称
	 */
	@ApiModelProperty(value = "真实名称")
	@Length(max = AbstractUser.MAX_REAL_NAMEE_LENGTH, message = "真实名称长度不能超过" + AbstractUser.MAX_REAL_NAMEE_LENGTH
			+ "个字。")
	@ExcelColumn(order = 2, friendlyName = "真实名称", width = 100)
	private String realName;

	/**
	 * 用户昵称
	 */
	@ApiModelProperty(value = "用户昵称")
	@Length(max = AbstractUser.MAX_NICK_NAME_LENGTH, message = "用户昵称长度不能超过" + AbstractUser.MAX_NICK_NAME_LENGTH + "个字。")
	@ExcelColumn(order = 3, friendlyName = "昵称", width = 100)
	private String nickName;

	/**
	 * 电话号码
	 */
	@ApiModelProperty(value = "手机号码")
	@Length(max = AbstractUser.MAX_PHONE_NUMBER_LENGTH, message = "手机电话不能超过" + AbstractUser.MAX_PHONE_NUMBER_LENGTH
			+ "个字。")
	@ExcelColumn(order = 4, friendlyName = "手机号码", width = 90)
	@MobilePhone
	private String phoneNumber;

	/**
	 * 邮件地址
	 */
	@ApiModelProperty(value = "邮件地址")
	@Length(max = AbstractUser.MAX_EMAIL_ADDRESS_LENGTH, message = "邮件地址不能超过" + AbstractUser.MAX_EMAIL_ADDRESS_LENGTH
			+ "个字。")
	@EmailAddress
	@ExcelColumn(order = 5, friendlyName = "邮件地址", width = 120)
	private String emailAddress;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	@Length(max = AbstractUser.MAX_SEX_LENGTH, message = "性别不能超过" + AbstractUser.MAX_SEX_LENGTH + "个字。")
	@ExcelColumn(order = 6, friendlyName = "性别", width = 60)
	private String sex;

	/**
	 * 头像路径
	 */
	@ApiModelProperty(value = "头像路径")
	@Length(max = AbstractUser.MAX_HEAD_PORTRAIT_PATH_LENGTH, message = "头像路径不能超过"
			+ AbstractUser.MAX_HEAD_PORTRAIT_PATH_LENGTH + "个字。")
	@ExcelColumn(order = 7, friendlyName = "头像路径", width = 200)
	private String headPortraitPath;

	/**
	 * 出生日期
	 */
	@ApiModelProperty(value = "出生日期")
	@ExcelColumn(order = 8, friendlyName = "出生日期", width = 90)
	private Date birthday;

	/**
	 * 获取用户名称
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名称
	 * 
	 * @param userName
	 *            用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取真实名称
	 * 
	 * @return
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置真实名称
	 * 
	 * @param realName
	 *            真实名称
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取昵称
	 * 
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 设置昵称
	 * 
	 * @param nickName
	 *            用户昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 获取手机号码
	 * 
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 设置手机号码
	 * 
	 * @param phoneNumber
	 *            手机号码
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 获取邮件地址
	 * 
	 * @return
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * 设置邮件地址
	 * 
	 * @param emailAddress
	 *            邮件地址
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * 获取性别
	 * 
	 * @return
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置性别
	 * 
	 * @param sex
	 *            性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取头像路径
	 * 
	 * @return
	 */
	public String getHeadPortraitPath() {
		return headPortraitPath;
	}

	/**
	 * 设置头像路径
	 * 
	 * @param headPortraitPath
	 */
	public void setHeadPortraitPath(String headPortraitPath) {
		this.headPortraitPath = headPortraitPath;
	}

	/**
	 * 获取出生日期
	 * 
	 * @return
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置出生日期
	 * 
	 * @param birthday
	 *            出生日期
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
