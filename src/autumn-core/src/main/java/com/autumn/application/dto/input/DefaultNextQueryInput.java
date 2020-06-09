package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

/**
 * 向后查询输入
 * 
 * @author 老码农
 *
 *         2018-04-03 10:54:13
 */
@ToString(callSuper = true)
public class DefaultNextQueryInput implements NextQueryInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4119896968022636159L;

	@FriendlyProperty(value = "最后行号")
	private Integer lastRowNumber;

	@Override
	public Integer getLastRowNumber() {
		return this.lastRowNumber;
	}

	@Override
	public void setLastRowNumber(Integer lastRowNumber) {
		if (lastRowNumber == null) {
			lastRowNumber = 0;
		}
		this.lastRowNumber = lastRowNumber;
	}

}
