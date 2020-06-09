package com.autumn.word.impl;

import org.apache.poi.xwpf.usermodel.XWPFTableCell;

/**
 * 表格动态单元格表达式
 * 
 * @author 老码农 2019-04-20 19:19:08
 */
class TableDynamicCellExcpression {

	private final XWPFTableCell cell;
	private final int colIndex;
	private final String excpression;
	private final boolean isMergeRow;

	/**
	 * 合并行
	 * 
	 * @param cell
	 * @param colIndex
	 * @param excpression
	 * @param isMergeRow
	 */
	public TableDynamicCellExcpression(XWPFTableCell cell, int colIndex, String excpression, boolean isMergeRow) {
		super();
		this.cell = cell;
		this.colIndex = colIndex;
		this.excpression = excpression;
		this.isMergeRow = isMergeRow;
	}

	public XWPFTableCell getCell() {
		return cell;
	}

	public int getColIndex() {
		return colIndex;
	}

	public String getExcpression() {
		return excpression;
	}

	/**
	 * 是否合并行
	 * 
	 * @return
	 */
	public boolean isMergeRow() {
		return isMergeRow;
	}

}
