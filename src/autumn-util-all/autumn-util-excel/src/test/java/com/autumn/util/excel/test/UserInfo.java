package com.autumn.util.excel.test;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelNonColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author JuWa ▪ Zhang
 * @date 2017年12月6日
 */
@ExcelWorkSheet(exportTitle = "用户信息", sheetName = "数据表格", exportExplain = "2017-2018年度")
public class UserInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7637400923764146240L;

//	@ExcelColumn(groupName = "用户", friendlyName = "用户Id")
	@ExcelColumn(friendlyName = "用户Id")
	private int userId;

//	@ExcelColumn(groupName = "用户", friendlyName = "用户名称", isMergeContentRow = true, isImportColumn = true)
	@ExcelColumn( friendlyName = "用户名称", isMergeContentRow = true, isImportColumn = true)
	private String userName;

	@ExcelColumn(friendlyName = "年龄")
	@Min(value = 5, message = "age必须大于5")
//	@NotNull(message = "age不能为空")
	private Integer age;

	@ExcelColumn( friendlyName = "a1属性")
	private String a1;

	/**
	 * 空白也合并
	 */
	@ExcelColumn( isMergeContentRow = true, isMergeBlankContentRow = true, friendlyName = "a2属性")
	private String a2;

	/**
	 * 空白不合并
	 */
//	@ExcelColumn(groupName = "A", isMergeContentRow = true)
	@ExcelNonColumn
	private String a3;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", age=" + age + ", a1=" + a1 + ", a2="
				+ a2 + ", a3=" + a3 + "]";
	}

}
