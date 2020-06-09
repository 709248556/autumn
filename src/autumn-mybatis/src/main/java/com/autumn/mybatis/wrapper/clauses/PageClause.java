package com.autumn.mybatis.wrapper.clauses;

import com.autumn.exception.ExceptionUtils;

/**
 * 分页查询
 * @author 老码农
 *
 * 2017-12-06 13:24:11
 */
public class PageClause extends AbstractClause {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9012066125020164213L;
	private int offset;
	private int limit;

	/**
	 * 
	 */
	public PageClause() {
	}

	/**
	 * 
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            限制行数
	 */
	public PageClause(int offset, int limit) {
		this.setOffset(offset);
		this.setLimit(limit);
	}

	/**
	 * 获取偏移量
	 * 
	 * @return
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * 设置偏移量
	 * 
	 * @param offset
	 *            偏移量
	 */
	public void setOffset(int offset) {
		if (offset < 0) {
			ExceptionUtils.throwArgumentOverflowException("offset", "offset 不能小于零。");
		}
		this.offset = offset;
	}

	/**
	 * 获取限制行数
	 * 
	 * @return
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 设置限制行数
	 * 
	 * @param limit
	 */
	public void setLimit(int limit) {
		if (limit < 0) {
			ExceptionUtils.throwArgumentOverflowException("limit", "limit 不能小于1。");
		}
		this.limit = limit;
	}

}
