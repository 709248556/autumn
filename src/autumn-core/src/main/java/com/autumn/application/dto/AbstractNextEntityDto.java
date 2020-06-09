package com.autumn.application.dto;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

/**
 * 向后查询实体抽象
 * 
 * @author 老码农
 *
 *         2018-04-03 10:00:40
 */
@ToString(callSuper = true)
public abstract class AbstractNextEntityDto implements NextEntityDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814502148600029865L;

	@FriendlyProperty(value = "行号")
	private int rowNumber;

	@Override
	public int getRowNumber() {
		return rowNumber;
	}

	@Override
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
}
