package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 向后查询输入
 * 
 * @author 老码农
 *
 *         2018-04-03 10:30:24
 */
public interface NextQueryInput extends Serializable {

	/**
	 * 获取最后行编号
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "最后行号")
	Integer getLastRowNumber();

	/**
	 * 设置最后行编号
	 * 
	 * @param lastRowNumber
	 *            最后行编号
	 *
	 */
	void setLastRowNumber(Integer lastRowNumber);
}
