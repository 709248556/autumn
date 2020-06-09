package com.autumn.application.dto;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 向后查询实体Dto
 * 
 * @author 老码农
 *
 *         2018-04-03 09:50:50
 */
public interface NextEntityDto extends Serializable {

	/**
	 * 获取行编号
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "行号")
	int getRowNumber();

	/**
	 * 设置行编号
	 * 
	 * @param row_Number
	 *            行编号
	 *
	 */
	void setRowNumber(int row_Number);
}
