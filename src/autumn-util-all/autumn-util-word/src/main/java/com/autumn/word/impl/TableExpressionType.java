package com.autumn.word.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 表格表达式类型
 * 
 * @author 老码农 2019-04-20 17:10:03
 */
class TableExpressionType {

	/**
	 * 表达式类型
	 * 
	 * @author 老码农 2019-04-20 17:11:07
	 */
	public enum ExpressionType {
		/**
		 * 无表达式
		 */
		NONE(0),
		/**
		 * 常规表达式
		 */
		GENERAL(1),
		/**
		 * 动态表达式
		 */
		DYNAMIC(2),
		/**
		 * 常规表动态
		 */
		GENERAL_DYNAMIC(3);

		private final int type;

		/**
		 * 
		 * @param type
		 */
		private ExpressionType(int type) {
			this.type = type;
		}

		/**
		 * 获取类型
		 * 
		 * @return
		 */
		public int getType() {
			return type;
		}
	}

	/**
	 * 创建 None 空白表达式类型
	 * 
	 * @return
	 */
	public static TableExpressionType createNone() {
		return new TableExpressionType(TableExpressionType.ExpressionType.NONE, null, -1);
	}

	/**
	 * 创建常规表达式类型
	 * 
	 * @return
	 */
	public static TableExpressionType createGeneral() {
		return new TableExpressionType(TableExpressionType.ExpressionType.GENERAL, null, -1);
	}

	/**
	 * 创建动态表达式类型
	 * 
	 * @param functionFow
	 *            函数行
	 * @param beginRow
	 *            开始行
	 * @return
	 */
	public static TableExpressionType createDynamic(XWPFTableRow functionFow, int beginRow) {
		return new TableExpressionType(TableExpressionType.ExpressionType.DYNAMIC, functionFow, beginRow);
	}

	/**
	 * 创建混合表达式类型
	 * 
	 * @param functionFow
	 *            函数行
	 * @param beginRow
	 *            开始行
	 * @return
	 */
	public static TableExpressionType createGeneralAndDynamic(XWPFTableRow functionFow, int beginRow) {
		return new TableExpressionType(TableExpressionType.ExpressionType.GENERAL_DYNAMIC, functionFow, beginRow);
	}

	private final ExpressionType expressionType;

	private final XWPFTableRow functionFow;

	private final int beginRow;

	private List<XWPFTableCell> generalCells;

	private List<TableDynamicCellExcpression> dynamicCells;

	private String dynamicMemberName;

	public TableExpressionType(ExpressionType expressionType, XWPFTableRow functionFow, int beginRow) {
		super();
		this.expressionType = expressionType;
		this.functionFow = functionFow;
		this.beginRow = beginRow;
		this.setGeneralCells(new ArrayList<>());
		this.setDynamicCells(new ArrayList<>());
	}

	/**
	 * 获取表达式类型
	 * 
	 * @return
	 */
	public final ExpressionType getExpressionType() {
		return expressionType;
	}

	/**
	 * 获取函数行
	 * 
	 * @return
	 */
	public final XWPFTableRow getFunctionFow() {
		return functionFow;
	}

	/**
	 * 获取开始行号
	 * 
	 * @return
	 */
	public final int getBeginRow() {
		return beginRow;
	}

	public List<XWPFTableCell> getGeneralCells() {
		return generalCells;
	}

	public void setGeneralCells(List<XWPFTableCell> generalCells) {
		this.generalCells = generalCells;
	}

	public List<TableDynamicCellExcpression> getDynamicCells() {
		return dynamicCells;
	}

	public void setDynamicCells(List<TableDynamicCellExcpression> dynamicCells) {
		this.dynamicCells = dynamicCells;
	}

	/**
	 * 获取动态成员名称
	 * 
	 * @return
	 */
	public String getDynamicMemberName() {
		return dynamicMemberName;
	}

	/**
	 * 设置动态成员名称
	 * 
	 * @param dynamicMemberName
	 *            动态成员名称
	 */
	public void setDynamicMemberName(String dynamicMemberName) {
		this.dynamicMemberName = dynamicMemberName;
	}
}
