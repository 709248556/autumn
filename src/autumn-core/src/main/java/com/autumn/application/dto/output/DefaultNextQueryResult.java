package com.autumn.application.dto.output;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.application.dto.NextEntityDto;
import lombok.ToString;

import java.util.List;

/**
 * 向后查询结果
 * 
 * @author 老码农
 *
 *         2018-04-03 11:08:41
 */
@ToString(callSuper = true)
public class DefaultNextQueryResult<T extends NextEntityDto> implements NextQueryResult<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8922014819967757163L;

	@FriendlyProperty(value = "项目集合")
	private List<T> items;
	@FriendlyProperty(value = "开始行号")
	private final int startRowNumber;
	@FriendlyProperty(value = "限制记录数")
	private final int limit;
	@FriendlyProperty(value = "结束行号")
	private int endRowNumber;

	/**
	 * 实例化
	 * 
	 * @param startRowNumber
	 * @param limit
	 */
	public DefaultNextQueryResult(int startRowNumber, int limit) {
		if (startRowNumber < 0) {
			startRowNumber = 0;
		}
		if (limit < 1) {
			limit = 1;
		}
		this.startRowNumber = startRowNumber;
		this.limit = limit;
		this.endRowNumber = startRowNumber;
	}

	@Override
	public final int getStartRowNumber() {
		return this.startRowNumber;
	}

	@Override
	public final int getEndRowNumber() {
		return this.endRowNumber;
	}

	@Override
	public final int getLimit() {
		return this.limit;
	}

	@Override
	public final List<T> getItems() {
		return this.items;
	}

	/**
	 * 设置项目集合
	 * 
	 * @param items
	 *
	 */
	public final void setItems(List<T> items) {
		this.items = items;
		if (items != null && items.size() > 0) {
			T item = items.get(items.size() - 1);
			this.endRowNumber = item.getRowNumber();
		} else {
			this.endRowNumber = this.getStartRowNumber();
		}
	}
}
