package com.autumn.application.dto.output;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.application.dto.NextEntityDto;

import java.io.Serializable;
import java.util.List;

/**
 * 向后查询结果
 * 
 * @author 老码农
 *
 *         2018-04-03 09:52:17
 */
public interface NextQueryResult<T extends NextEntityDto> extends Serializable {

	/**
	 * 获取开始行编号
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "开始行号")
	int getStartRowNumber();

	/**
	 * 获取结束行编号
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "结束行号")
	int getEndRowNumber();

	/**
	 * 获取限制大小
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "限制大小")
	int getLimit();

	/**
	 * 获取项目集合
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "项目集合")
	List<T> getItems();
}
