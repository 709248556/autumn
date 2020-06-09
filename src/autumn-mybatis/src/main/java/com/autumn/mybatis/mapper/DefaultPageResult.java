package com.autumn.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 默认分页结果
 * 
 * @author 老码农 2019-06-11 02:18:22
 * @param <T>
 *            类型
 */
public class DefaultPageResult<T> implements PageResult<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1721200229747507685L;

	@JSONField(ordinal = 1)
	private int currentPage;

	@JSONField(ordinal = 2)
	private int pageSize;

	@JSONField(ordinal = 3)
	private int pageTotal;

	@JSONField(ordinal = 4)
	private int pageRecords;

	@JSONField(ordinal = 5)
	private int rowTotal;

	@JSONField(ordinal = 6)
	private int startRow;

	@JSONField(ordinal = 7)
	private int endRow;

	@JSONField(ordinal = 8)
	private List<T> items;

	/**
	 * 实例化页查询结果
	 * 
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            页大小
	 * @param rowTotal
	 *            行总数
	 */
	public DefaultPageResult(int currentPage, int pageSize, int rowTotal) {
		if (pageSize < 1) {
			pageSize = 1;
		}
		if (rowTotal < 0) {
			rowTotal = 0;
		}
		this.pageSize = pageSize;
		this.rowTotal = rowTotal;
		int count = this.rowTotal / this.pageSize;
		if (this.rowTotal % this.pageSize != 0) {
			count++;
		}
		if (count < 1) {
			count = 1;
		}
		this.pageTotal = count;
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > this.pageTotal) {
			currentPage = this.pageTotal;
		}
		this.currentPage = currentPage;
		if (rowTotal > 0) {
			this.startRow = (currentPage - 1) * pageSize + 1;
			this.endRow = Math.min(this.startRow + pageSize - 1, Math.max(rowTotal, this.startRow));
			this.pageRecords = this.endRow - this.startRow + 1;
		} else {
			this.startRow = 0;
			this.endRow = 0;
			this.pageRecords = 0;
		}
		this.setItems(new ArrayList<>());
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public int getPageTotal() {
		return this.pageTotal;
	}

	@Override
	public int getPageRecords() {
		return this.pageRecords;
	}

	@Override
	public int getRowTotal() {
		return this.rowTotal;
	}

	@Override
	public int getStartRow() {
		return this.startRow;
	}

	@Override
	public int getEndRow() {
		return this.endRow;
	}

	@Override
	public List<T> getItems() {
		return items;
	}

	/**
	 * 设置项目集合
	 * 
	 * @param items
	 */
	public void setItems(List<T> items) {
		this.items = items;
	}
}
